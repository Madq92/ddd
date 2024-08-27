package cc.mikaka.ddd.common.sequence;


/**
 * 唯一序列号生成服务
 */
public interface SequenceService {
    /**
     * 生成唯－序列号
     */
    String buildSequenceId(SequenceIdEnum sequenceIdEnum);
}
