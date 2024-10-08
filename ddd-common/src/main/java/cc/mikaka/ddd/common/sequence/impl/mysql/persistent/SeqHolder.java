package cc.mikaka.ddd.common.sequence.impl.mysql.persistent;


import cc.mikaka.ddd.common.sequence.impl.mysql.core.LongSeqPool;
import cc.mikaka.ddd.common.sequence.impl.mysql.core.Sequence;
import cc.mikaka.ddd.common.sequence.impl.mysql.core.exceptions.SeqException;
import cc.mikaka.ddd.common.sequence.impl.mysql.core.SeqFormatter;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 取号器
 *
 * @author CJ (power4j@outlook.com)
 * @date 2020/7/3
 * @since 1.0
 */
public class SeqHolder implements Sequence<Long> {

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    private final SeqSynchronizer seqSynchronizer;

    private final String name;

    private final PartitionFormatter partitionFunc;

    private final long initValue;

    private final int poolSize;

    private final SeqFormatter seqFormatter;

    private final AtomicLong pollCount = new AtomicLong();

    private final AtomicReference<String> currentPartitionValueRef = new AtomicReference<>();

    private volatile LongSeqPool seqPool;

    /**
     * 构造方法
     *
     * @param seqSynchronizer 同步器
     * @param name            名称
     * @param partitionFunc   分区函数
     * @param initValue       初始值,号池不存在时使用
     * @param poolSize        表示单次申请序号数量
     * @param seqFormatter    自定义格式化输出，可选
     */
    public SeqHolder(SeqSynchronizer seqSynchronizer, String name, PartitionFormatter partitionFunc, long initValue,
                     int poolSize, SeqFormatter seqFormatter) {
        this.seqSynchronizer = Objects.requireNonNull(seqSynchronizer);
        this.name = Objects.requireNonNull(name);
        this.partitionFunc = partitionFunc;
        this.initValue = initValue;
        this.poolSize = poolSize;
        this.seqFormatter = seqFormatter == null ? SeqFormatter.DEFAULT_FORMAT : seqFormatter;
    }

    /**
     * 构造方法
     *
     * @param seqSynchronizer 同步器
     * @param name            名称
     * @param partition       分区名称
     * @param initValue       初始值,号池不存在时使用
     * @param poolSize        表示单次申请序号数量
     * @param seqFormatter    自定义格式化输出，可选
     */
    public SeqHolder(SeqSynchronizer seqSynchronizer, String name, String partition, long initValue, int poolSize,
                     SeqFormatter seqFormatter) {
        this(seqSynchronizer, name, () -> partition, initValue, poolSize, seqFormatter);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String getName() {
        rwLock.readLock().lock();
        try {
            return seqPool == null ? name : seqPool.getName();
        } finally {
            rwLock.readLock().unlock();
        }
    }

    @Override
    public Optional<Long> nextOpt() {
        final String nextPartitionValue = computePartitionValue();
        Optional<Long> val;
        rwLock.readLock().lock();
        try {
            if (nextPartitionValue.equals(currentPartitionValueRef.get())) {
                val = (seqPool == null ? Optional.empty() : seqPool.nextOpt());
                if (val.isPresent()) {
                    return val;
                }
            }
        } finally {
            rwLock.readLock().unlock();
        }
        return pull(nextPartitionValue);
    }

    @Override
    public Long next() {
        return nextOpt().get();
    }

    @Override
    public Optional<String> nextStrOpt() throws SeqException {
        return nextOpt().map(n -> seqFormatter.format(name, currentPartitionValueRef.get(), n));
    }

    /**
     * 默认的初始化是懒加载,执行此方法可以手动初始化
     */
    public void prepare() {
        rwLock.writeLock().lock();
        try {
            if (seqPool == null) {
                seqPool = fetch(computePartitionValue());
            }
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    /**
     * 从后端拉取值的次数
     *
     * @return
     */
    public long getPullCount() {
        return pollCount.get();
    }

    private Optional<Long> pull(String partitionValue) {
        Optional<Long> val;
        rwLock.writeLock().lock();
        try {
            if (seqPool == null || !partitionValue.equals(currentPartitionValueRef.get())) {
                seqPool = fetch(partitionValue);
            }
            val = seqPool.nextOpt();
            if (!val.isPresent()) {
                val = (seqPool = fetch(partitionValue)).nextOpt();
                if (!val.isPresent()) {
                    throw new IllegalStateException("Bug detected : " + seqPool.toString());
                }
            }
            return val;
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    private LongSeqPool fetch(String partitionValue) {
        pollCount.incrementAndGet();
        LongSeqPool seqPool;
        if (seqSynchronizer.tryCreate(name, partitionValue, initValue + poolSize)) {
            seqPool = LongSeqPool.forRange(makePoolName(name, partitionValue), initValue, initValue + poolSize - 1,
                    false);
        } else {
            AddState state = seqSynchronizer.tryAddAndGet(name, partitionValue, poolSize, -1);
            seqPool = LongSeqPool.forRange(makePoolName(name, partitionValue), state.getPrevious(),
                    state.getCurrent() - 1, false);
        }
        currentPartitionValueRef.set(partitionValue);
        return seqPool;
    }

    private String makePoolName(String seqName, String window) {
        return seqName + "/" + window;
    }

    private String computePartitionValue() {
        return partitionFunc.get();
    }

    public static class Builder {

        private SeqSynchronizer synchronizer;

        private String name;

        private PartitionFormatter partitionFunc;

        private long initValue = 1;

        private int poolSize = 1;

        private SeqFormatter seqFormatter = ((seqName, partition, value) -> String.format("%s.%s.%08d", seqName,
                partition, value));

        public Builder synchronizer(SeqSynchronizer synchronizer) {
            this.synchronizer = synchronizer;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder partitionFunc(PartitionFormatter partitionFunc) {
            this.partitionFunc = partitionFunc;
            return this;
        }

        public Builder initValue(long initValue) {
            this.initValue = initValue;
            return this;
        }

        public Builder poolSize(int poolSize) {
            this.poolSize = poolSize;
            return this;
        }

        public Builder seqFormatter(SeqFormatter seqFormatter) {
            this.seqFormatter = seqFormatter;
            return this;
        }

        public SeqHolder build() {
            return new SeqHolder(synchronizer, name, partitionFunc, initValue, poolSize, seqFormatter);
        }

    }

}
