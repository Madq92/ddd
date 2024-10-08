package cc.mikaka.ddd.common.sequence.impl.mysql.core;

import cc.mikaka.ddd.common.sequence.impl.mysql.core.exceptions.SeqException;
import lombok.Getter;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 序号池(Long型)
 * <ul>
 * <li>取值范围{@code [0,Long.MAX_VALUE]}</li>
 * <li>支持一次性取号和循环取号</li>
 * <li>线程安全,lock free</li>
 * </ul>
 */
public class LongSeqPool implements SeqPool<Long, LongSeqPool> {

    public final static long MAX_VALUE = Long.MAX_VALUE;
    /**
     * 表示一个号池外的值
     */
    public final static long OUT_OF_POOL = Long.MIN_VALUE;
    private final static long ZERO = 0L;
    public final static long MIN_VALUE = ZERO;
    private final static long ONE = 1L;
    private final String name;

    @Getter
    private final long start;

    @Getter
    private final long end;

    @Getter
    private final boolean reRoll;

    private final AtomicLong current;

    /**
     * constructor
     *
     * @param name   名称
     * @param start  起始值，包含
     * @param end    结束值，包含
     * @param reRoll 是否允许滚动，可以滚动的号池永远不会耗尽
     */
    private LongSeqPool(String name, long start, long end, boolean reRoll) {
        assertMinValue(start, "Invalid start value: " + start);
        this.name = name;
        this.start = start;
        this.end = end;
        this.reRoll = reRoll;
        this.current = new AtomicLong(start);
        if (end < start) {
            throw new IllegalArgumentException("Nothing to offer");
        }
    }

    /**
     * 根据数量创建
     *
     * @param name   名称
     * @param start  起始值，包含
     * @param size   数量
     * @param reRoll 是否允许滚动
     * @return LongSeqPool
     */
    public static LongSeqPool forSize(String name, long start, int size, boolean reRoll) {
        return new LongSeqPool(name, start, start + size - ONE, reRoll);
    }

    /**
     * 根据区间创建
     *
     * @param name   名称
     * @param min    起始值，包含
     * @param max    end 结束值，包含
     * @param reRoll 是否允许滚动
     * @return LongSeqPool
     */
    public static LongSeqPool forRange(String name, long min, long max, boolean reRoll) {
        return new LongSeqPool(name, min, max, reRoll);
    }

    /**
     * 根据起始值创建,最大值为 Long.MAX_VALUE
     *
     * @param name   名称
     * @param start  起始值，包含
     * @param reRoll 是否允许滚动
     * @return LongSeqPool
     */
    public static LongSeqPool startFrom(String name, long start, boolean reRoll) {
        return new LongSeqPool(name, start, Long.MAX_VALUE, reRoll);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long next() throws SeqException {
        return take();
    }

    @Override
    public Optional<Long> nextOpt() {
        final long val = take(OUT_OF_POOL);
        return Optional.ofNullable(OUT_OF_POOL == val ? null : val);
    }

    @Override
    public Long peek() {
        return current.get();
    }

    /**
     * 取出序号
     *
     * @return 返回下一个序号
     * @throws SeqException 号池耗尽抛出异常，可以通过 {@code hasMore} 方法提前检查
     * @see SeqPool#hasMore
     */
    public long take() {
        final long val = take(OUT_OF_POOL);
        if (val == OUT_OF_POOL) {
            long current = peek();
            throw new SeqException(String.format("No more value,current = %08d(%d/%d)", current, start, end));
        }
        return val;
    }

    /**
     * 取出序号
     *
     * @param defVal 号池耗尽时返回的默认值，必须是号池范围外的值,推荐使用 {@code OUT_OF_POOL}
     * @return 返回下一个序号, 号池耗尽返回默认值
     * @throws IllegalArgumentException defVal 无效
     * @see LongSeqPool#OUT_OF_POOL
     */
    public long take(long defVal) {
        if (defVal >= start && defVal <= end) {
            throw new IllegalArgumentException("Bad defVal");
        }
        long val = current.getAndUpdate(this::updateFunc);
        return (val > end || val < start) ? defVal : val;
    }

    private long updateFunc(final long pre) {
        if (reRoll && pre >= end) {
            return minValue();
        }
        return pre + ONE;
    }

    @Override
    public boolean hasMore() {
        return remaining() > ZERO;
    }

    @Override
    public LongSeqPool fork(String name) {
        LongSeqPool seqPool = new LongSeqPool(name, start, end, reRoll);
        seqPool.setCurrent(peek());
        return seqPool;
    }

    @Override
    public long remaining() {
        return reRoll ? capacity() : end - peek() + ONE;
    }

    @Override
    public long capacity() {
        return end - start + ONE;
    }

    @Override
    public Long minValue() {
        return start;
    }

    @Override
    public Long maxValue() {
        return end;
    }

    private void setCurrent(long val) {
        current.set(val);
    }

    @Override
    public String toString() {
        return peek() + " -> [" + start + "," + end + "],reRoll = " + reRoll;
    }

    protected void assertMinValue(long val, String msg) {
        if (val < MIN_VALUE) {
            throw new SeqException(msg);
        }
    }

}
