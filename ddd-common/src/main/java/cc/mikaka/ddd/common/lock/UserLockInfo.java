package cc.mikaka.ddd.common.lock;

import cc.mikaka.ddd.common.constants.CommonConstants;
import lombok.Data;

@Data
public class UserLockInfo extends BaseLockInfo {
    private static final String BIZ_TYPE = "USER";
    private String userId;

    @Override
    public String toLockKey() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(BIZ_TYPE).append(CommonConstants.COLON).append(userId);
        return stringBuilder.toString();
    }

    @Override
    public String getErrorMessage() {
        return "当前用户正在编辑";
    }
}
