package cc.mikaka.ddd.common.sequence.impl;

import cc.mikaka.ddd.common.sequence.SequenceIdEnum;
import cc.mikaka.ddd.common.sequence.SequenceService;

import java.util.UUID;

public class UuidSequenceService implements SequenceService {
    @Override
    public String buildSequenceId(SequenceIdEnum sequenceIdEnum) {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
