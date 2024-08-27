package cc.mikaka.ddd.common.sequence.impl;

import cc.mikaka.ddd.common.sequence.SequenceIdEnum;
import cc.mikaka.ddd.common.sequence.SequenceService;
import cc.mikaka.ddd.common.sequence.impl.mysql.core.SeqFormatter;
import cc.mikaka.ddd.common.sequence.impl.mysql.core.Sequence;
import cc.mikaka.ddd.common.sequence.impl.mysql.ext.InMemorySequenceRegistry;
import cc.mikaka.ddd.common.sequence.impl.mysql.ext.SequenceRegistry;
import cc.mikaka.ddd.common.sequence.impl.mysql.persistent.PartitionFormatter;
import cc.mikaka.ddd.common.sequence.impl.mysql.persistent.SeqHolder;
import cc.mikaka.ddd.common.sequence.impl.mysql.persistent.SeqSynchronizer;

public class DbSequenceService implements SequenceService {
    private final static SeqFormatter MY_FORMATTER = (seqName, partition, value) -> String.valueOf(value);
    private final PartitionFormatter partitionFunc = PartitionFormatter.NONE;
    private final SeqSynchronizer seqSynchronizer;
    private final SequenceRegistry<Long, Sequence<Long>> sequenceRegistry = new InMemorySequenceRegistry<>();

    public DbSequenceService(SeqSynchronizer seqSynchronizer) {
        this.seqSynchronizer = seqSynchronizer;
    }

    private Sequence<Long> createSequence(String name) {
        return new SeqHolder(seqSynchronizer, name, partitionFunc, 1L, 3, MY_FORMATTER);
    }

    @Override
    public String buildSequenceId(SequenceIdEnum sequenceIdEnum) {
        return sequenceRegistry.getOrRegister(sequenceIdEnum.getBizLabel(), this::createSequence).nextStr();
    }
}
