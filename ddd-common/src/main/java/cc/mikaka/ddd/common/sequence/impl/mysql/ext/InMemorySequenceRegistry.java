package cc.mikaka.ddd.common.sequence.impl.mysql.ext;



import cc.mikaka.ddd.common.sequence.impl.mysql.core.Sequence;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

public class InMemorySequenceRegistry<T, S extends Sequence<T>> implements SequenceRegistry<T, S> {

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    private final Lock rLock = rwLock.readLock();

    private final Lock wLock = rwLock.writeLock();

    /**
     * Guard with rwLock,So we don't need ConcurrentHashMap. TODO: GC
     */
    private final Map<String, S> map = new HashMap<>(8);

    @Override
    public Optional<S> register(String name, S seq) {
        assert Objects.nonNull(seq);
        wLock.lock();
        try {
            return Optional.ofNullable(map.put(name, seq));
        } finally {
            wLock.unlock();
        }
    }

    @Override
    public Optional<S> get(String name) {
        rLock.lock();
        try {
            return Optional.ofNullable(map.get(name));
        } finally {
            rLock.unlock();
        }
    }

    @Override
    public Optional<S> remove(String name) {
        wLock.lock();
        try {
            return Optional.ofNullable(map.remove(name));
        } finally {
            wLock.unlock();
        }
    }

    @Override
    public S getOrRegister(String name, Function<String, S> func) {
        S seq = get(name).orElse(null);
        if (Objects.isNull(seq)) {
            wLock.lock();
            try {
                seq = map.get(name);
                if (Objects.isNull(seq)) {
                    seq = func.apply(name);
                    assert Objects.nonNull(seq);
                    map.put(name, seq);
                }
            } finally {
                wLock.unlock();
            }
        }
        return seq;
    }

    @Override
    public int size() {
        rLock.lock();
        try {
            return map.size();
        } finally {
            rLock.unlock();
        }
    }

}
