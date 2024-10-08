package cc.mikaka.ddd.common.sequence.impl.mysql.ext;

import cc.mikaka.ddd.common.sequence.impl.mysql.core.Sequence;

import java.util.Optional;
import java.util.function.Function;

/**
 * Sequence 注册表
 */
public interface SequenceRegistry<T, S extends Sequence<T>> {

    /**
     * 注册 {@code Sequence} 对象,如果名称相同，则之前的对象被替换
     *
     * @param name 名称
     * @param seq  当{@code Sequence}
     * @return 返回之前的{@code Sequence} 对象
     */
    Optional<S> register(String name, S seq);

    /**
     * 返回一个已经注册的 {@code Sequence} 对象
     *
     * @param name 名称
     * @return 返回Sequence 对象,如果不存在则返回 {@code Optional.empty()}
     */
    Optional<S> get(String name);

    /**
     * 从注册表中移除已经注册的 {@code Sequence} 对象
     *
     * @param name 名称
     * @return 返回被删除的对象, 如果不存在则返回 {@code Optional.empty()}
     */
    Optional<S> remove(String name);

    /**
     * 返回一个已经注册的 {@code Sequence} 对象,如果不存在则将新的对象注册
     *
     * @param name 名称
     * @param func 当不存在指定名称的{@code Sequence} 对象时，由该函数创建对象实例,该函数的入参为name
     * @return 返回{@code Sequence} 对象
     */
    S getOrRegister(String name, Function<String, S> func);

    /**
     * 统计数量
     *
     * @return 返回数量
     */
    int size();

}
