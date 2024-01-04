package cc.mikaka.ddd.processor.user;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cc.mikaka.ddd.bean.dto.UserDTO;
import cc.mikaka.ddd.bean.request.user.CreateUserRequest;
import cc.mikaka.ddd.common.context.ParamContext;
import cc.mikaka.ddd.common.lock.BaseLockInfo;
import cc.mikaka.ddd.common.util.AssertUtil;
import cc.mikaka.ddd.convertor.UserConvert;
import cc.mikaka.ddd.model.user.UserModel;
import cc.mikaka.ddd.processor.AbstractProcessor;
import cc.mikaka.ddd.repository.UserRepository;
import cc.mikaka.ddd.repository.condition.UserQueryCondition;

@Component
public class UserCreateProcessor extends AbstractProcessor<UserModel, CreateUserRequest, String> {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserConvert userConvert;

    @Override
    protected String doBusiness(UserModel userModel, ParamContext paramContext) {
        userRepository.insert(userModel);
        return userModel.getUserId();
    }

    @Override
    protected UserModel trans(CreateUserRequest request, ParamContext paramContext) {
        UserDTO user = request.getUser();
        UserModel userModel = userConvert.dto2Model(user);
        // ID,状态初始化
        userModel.setUserId(UUID.randomUUID().toString());
        userModel.setStatus("Online");
        return userModel;
    }

    @Override
    protected void preTransValidate(CreateUserRequest request, ParamContext paramContext) {
        UserDTO user = request.getUser();
        AssertUtil.notNull(user, "用户不能为空");
        AssertUtil.notNull(user.getUserName(), "用户名称不能为空");
    }

    @Override
    protected void afterTransValidate(UserModel userModel, ParamContext paramContext) {
        UserQueryCondition condition = new UserQueryCondition();
        condition.setUserName(userModel.getUserName());
        List<UserModel> userModels = userRepository.queryList(condition);
        AssertUtil.isEmpty(userModels, "用户名已被占用");
    }

    @Override
    protected List<BaseLockInfo> getLockKey(UserModel userModel, ParamContext paramContext) {
        return null;
    }
}
