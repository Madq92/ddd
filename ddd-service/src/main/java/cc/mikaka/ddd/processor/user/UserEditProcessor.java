package cc.mikaka.ddd.processor.user;

import java.util.List;
import java.util.stream.Collectors;

import cc.mikaka.ddd.common.enums.ActionType;
import cc.mikaka.ddd.common.enums.BizType;
import cc.mikaka.ddd.core.model.UserModel;
import cc.mikaka.ddd.core.repository.UserRepository;
import cc.mikaka.ddd.core.repository.condition.UserQueryCondition;
import cc.mikaka.ddd.processor.AbstractProcessor;
import cc.mikaka.ddd.processor.Processable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cc.mikaka.ddd.bean.dto.UserDTO;
import cc.mikaka.ddd.bean.request.user.CreateUserRequest;
import cc.mikaka.ddd.common.context.ParamContext;
import cc.mikaka.ddd.common.lock.BaseLockInfo;
import cc.mikaka.ddd.common.lock.UserLockInfo;
import cc.mikaka.ddd.common.util.AssertUtil;
import cc.mikaka.ddd.convertor.UserConvert;
import com.google.common.collect.Lists;

@Component
@Processable(bizType = BizType.USER, actionType = ActionType.EDIT)
public class UserEditProcessor extends AbstractProcessor<UserModel, CreateUserRequest, String> {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserConvert userConvert;

    @Override
    protected String doBusiness(UserModel userModel, ParamContext paramContext) {
        userRepository.update(userModel);
        return userModel.getUserId();
    }

    @Override
    protected UserModel trans(CreateUserRequest request, ParamContext paramContext) {
        return userConvert.dto2Model(request.getUser());
    }

    @Override
    protected void preTransValidate(CreateUserRequest request, ParamContext paramContext) {
        UserDTO user = request.getUser();
        AssertUtil.notNull(user, "用户不能为空");
        AssertUtil.notNull(user.getUserId(), "用户ID不能为空");
        AssertUtil.notNull(user.getUserName(), "用户名称不能为空");
    }

    @Override
    protected void afterTransValidate(UserModel userModel, ParamContext paramContext) {
        UserQueryCondition condition = new UserQueryCondition();
        condition.setUserName(userModel.getUserName());
        List<UserModel> userModelDbs = userRepository.queryList(condition);
        // 排除自己
        List<UserModel> checkUserModels =
            userModelDbs.stream().filter(userModelDb -> userModel.getUserId().equals(userModelDb.getUserId())).collect(Collectors.toList());
        AssertUtil.isEmpty(checkUserModels, "用户名已被占用");
    }

    @Override
    protected List<BaseLockInfo> getLockKey(UserModel userModel, ParamContext paramContext) {
        UserLockInfo userLockInfo = new UserLockInfo();
        userLockInfo.setUserId(userModel.getUserId());
        return Lists.newArrayList(userLockInfo);
    }
}
