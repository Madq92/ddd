package cc.mikaka.ddd.service.processor.user;

import cc.mikaka.ddd.bean.dto.UserDTO;
import cc.mikaka.ddd.bean.request.user.EditUserRequest;
import cc.mikaka.ddd.common.context.ParamContext;
import cc.mikaka.ddd.common.enums.ActionType;
import cc.mikaka.ddd.common.enums.BizType;
import cc.mikaka.ddd.common.lock.BaseLockInfo;
import cc.mikaka.ddd.common.lock.UserLockInfo;
import cc.mikaka.ddd.common.util.AssertUtil;
import cc.mikaka.ddd.core.model.UserModel;
import cc.mikaka.ddd.core.repository.UserRepository;
import cc.mikaka.ddd.core.repository.condition.UserQueryCondition;
import cc.mikaka.ddd.service.convertor.UserConvert;
import cc.mikaka.ddd.service.event.EventMessageModel;
import cc.mikaka.ddd.service.processor.AbstractProcessor;
import cc.mikaka.ddd.service.processor.Processable;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Processable(bizType = BizType.USER, actionType = ActionType.EDIT)
public class UserEditProcessor extends AbstractProcessor<UserModel, EditUserRequest, Boolean> {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserConvert userConvert;

    @Override
    protected Boolean doBusiness(UserModel userModel, ParamContext paramContext) {
        userRepository.update(userModel);
        return true;
    }

    @Override
    protected UserModel trans(EditUserRequest request, ParamContext paramContext) {
        return userConvert.dto2Model(request.getUser());
    }

    @Override
    protected void preTransValidate(EditUserRequest request, ParamContext paramContext) {
        UserDTO user = request.getUser();
        AssertUtil.notNull(user, "用户不能为空");
        AssertUtil.notNull(user.getUserId(), "用户ID不能为空");
        AssertUtil.notNull(user.getName(), "用户名称不能为空");
    }

    @Override
    protected void afterTransValidate(UserModel userModel, ParamContext paramContext) {
        UserQueryCondition condition = new UserQueryCondition();
        condition.setName(userModel.getName());
        List<UserModel> userModelDbs = userRepository.queryList(condition);
        if (CollectionUtils.isEmpty(userModelDbs)) {
            return;
        }
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

    @Override
    protected List<EventMessageModel> buryEventPoints(UserModel userModel, ParamContext paramContext) {
        EventMessageModel eventMessageModel = new EventMessageModel();
        eventMessageModel.setBaseEventModel(userModel);
        return Lists.newArrayList(eventMessageModel);
    }
}
