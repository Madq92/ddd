package cc.mikaka.ddd.service.processor.user;

import cc.mikaka.ddd.bean.dto.UserDTO;
import cc.mikaka.ddd.bean.enums.StateEnum;
import cc.mikaka.ddd.bean.request.user.CreateUserRequest;
import cc.mikaka.ddd.common.context.ParamContext;
import cc.mikaka.ddd.common.enums.ActionType;
import cc.mikaka.ddd.common.enums.BizType;
import cc.mikaka.ddd.common.lock.BaseLockInfo;
import cc.mikaka.ddd.common.sequence.SequenceIdEnum;
import cc.mikaka.ddd.common.sequence.SequenceService;
import cc.mikaka.ddd.common.util.AssertUtil;
import cc.mikaka.ddd.core.model.UserModel;
import cc.mikaka.ddd.core.repository.UserRepository;
import cc.mikaka.ddd.core.repository.condition.UserQueryCondition;
import cc.mikaka.ddd.service.convertor.UserConvert;
import cc.mikaka.ddd.service.event.EventMessageModel;
import cc.mikaka.ddd.service.processor.AbstractProcessor;
import cc.mikaka.ddd.service.processor.Processable;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Processable(bizType = BizType.USER, actionType = ActionType.CREATE)
public class UserCreateProcessor extends AbstractProcessor<UserModel, CreateUserRequest, String> {
    @Autowired
    SequenceService sequenceService;
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
        userModel.setUserId(sequenceService.buildSequenceId(SequenceIdEnum.USER));
        userModel.setStatus(StateEnum.ONLINE.getCode());
        return userModel;
    }

    @Override
    protected void preTransValidate(CreateUserRequest request, ParamContext paramContext) {
        UserDTO user = request.getUser();
        AssertUtil.notNull(user, "用户不能为空");
        AssertUtil.notNull(user.getName(), "用户名称不能为空");
    }

    @Override
    protected void afterTransValidate(UserModel userModel, ParamContext paramContext) {
        UserQueryCondition condition = new UserQueryCondition();
        condition.setName(userModel.getName());
        List<UserModel> userModels = userRepository.queryList(condition);
        AssertUtil.isEmpty(userModels, "用户名已被占用");
    }

    @Override
    protected List<BaseLockInfo> getLockKey(UserModel userModel, ParamContext paramContext) {
        return null;
    }

    @Override
    protected List<EventMessageModel> buryEventPoints(UserModel userModel, ParamContext paramContext) {
        EventMessageModel eventMessageModel = new EventMessageModel();
        eventMessageModel.setBaseEventModel(userModel);
        return Lists.newArrayList(eventMessageModel);
    }
}
