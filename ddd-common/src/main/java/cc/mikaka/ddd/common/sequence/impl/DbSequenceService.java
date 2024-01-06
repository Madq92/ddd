package cc.mikaka.ddd.common.sequence.impl;

import cc.mikaka.ddd.common.sequence.SequenceIdEnum;
import cc.mikaka.ddd.common.sequence.SequenceService;


public class DbSequenceService implements SequenceService {
    @Override
    public String buildSequenceId(SequenceIdEnum sequenceIdEnum) {
        //TODO 依赖数据库实现ID生成
        return null;
    }
}
