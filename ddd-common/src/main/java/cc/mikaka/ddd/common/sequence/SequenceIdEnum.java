package cc.mikaka.ddd.common.sequence;


import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 唯一序列号枚举
 */
@Getter
public enum SequenceIdEnum {
    DEFAULT("default", "默认"),
    USER("user", "用户表"),
    ;


    /**
     * 业务标识
     */
    private final String bizLabel;

    /**
     * 描述
     */
    private final String message;

    SequenceIdEnum(String bizLabel, String message) {
        this.bizLabel = bizLabel;
        this.message = message;
    }

    /**
     * 根据bizLabel获得枚举
     *
     * @param bizLabel
     * @return
     */
    public static SequenceIdEnum getByBizLabel(String bizLabel) {
        for (SequenceIdEnum each : values()) {
            if (StringUtils.equals(each.bizLabel, bizLabel)) {
                return each;
            }
        }
        return null;
    }
}
