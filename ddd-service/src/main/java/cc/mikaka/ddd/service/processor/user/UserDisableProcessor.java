package cc.mikaka.ddd.service.processor.user;

import cc.mikaka.ddd.bean.enums.StateEnum;
import cc.mikaka.ddd.bean.request.user.UserIdRequest;
import cc.mikaka.ddd.common.context.ParamContext;
import cc.mikaka.ddd.common.enums.ActionType;
import cc.mikaka.ddd.common.enums.BizType;
import cc.mikaka.ddd.common.lock.BaseLockInfo;
import cc.mikaka.ddd.common.lock.UserLockInfo;
import cc.mikaka.ddd.common.util.AssertUtil;
import cc.mikaka.ddd.core.model.UserModel;
import cc.mikaka.ddd.core.repository.UserRepository;
import cc.mikaka.ddd.service.processor.AbstractProcessor;
import cc.mikaka.ddd.service.processor.Processable;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Processable(bizType = BizType.USER, actionType = ActionType.DISABLE)
public class UserDisableProcessor extends AbstractProcessor<String, UserIdRequest, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    protected String doBusiness(String userId, ParamContext paramContext) {
        userRepository.updateStatus(userId, StateEnum.PAUSE.getDesc());
        return userId;
    }

    @Override
    protected String trans(UserIdRequest request, ParamContext paramContext) {
        return request.getUserId();
    }

    @Override
    protected void preTransValidate(UserIdRequest request, ParamContext paramContext) {
        AssertUtil.notNull(request.getUserId(), "用户ID不能为空");
    }

    @Override
    protected void afterTransValidate(String userId, ParamContext paramContext) {
        UserModel userModel = userRepository.queryByUserId(userId);
        AssertUtil.notNull(userModel, "用户不存在");
    }

    @Override
    protected List<BaseLockInfo> getLockKey(String userId, ParamContext paramContext) {
        UserLockInfo userLockInfo = new UserLockInfo();
        userLockInfo.setUserId(userId);
        return Lists.newArrayList(userLockInfo);
    }
}
