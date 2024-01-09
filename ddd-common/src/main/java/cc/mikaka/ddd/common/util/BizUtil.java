package cc.mikaka.ddd.common.util;

import cc.mikaka.ddd.common.constants.PaaSConstants;
import cc.mikaka.ddd.common.enums.ActionType;
import cc.mikaka.ddd.common.enums.BizType;

public class BizUtil {
    /**
     * 生成业务Key
     *
     * @param bizType
     * @param actionType
     * @return
     */
    public static String getBizKey(BizType bizType, ActionType actionType) {
        return bizType.getCode() + PaaSConstants.SEP_UNDERLINE + actionType.getCode();
    }
}
