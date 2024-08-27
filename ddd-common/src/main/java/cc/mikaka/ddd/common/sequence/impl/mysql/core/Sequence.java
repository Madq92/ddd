package cc.mikaka.ddd.common.sequence.impl.mysql.core;


import cc.mikaka.ddd.common.sequence.impl.mysql.core.exceptions.SeqException;

import java.util.Optional;

/**
 * 序号生成器
 */
public interface Sequence<T> {

    /**
     * 名称
     *
     * @return
     */
    String getName();

    /**
     * 取值
     *
     * @return
     * @throws SeqException 无法获得序号抛出异常
     */
    T next() throws SeqException;

    /**
     * 取值
     *
     * @return 无法获得序号返回 null
     */
    Optional<T> nextOpt();

    /**
     * 返回经过格式化后字符串
     *
     * @return
     * @throws SeqException 无法获得序号返回 null
     */
    default Optional<String> nextStrOpt() {
        return nextOpt().map(Object::toString);
    }

    /**
     * 返回经过格式化后字符串
     *
     * @return
     * @throws SeqException 无法获得序号抛出异常
     */
    default String nextStr() throws SeqException {
        return nextStrOpt().orElseThrow(() -> new SeqException("Nothing to offer"));
    }

}
