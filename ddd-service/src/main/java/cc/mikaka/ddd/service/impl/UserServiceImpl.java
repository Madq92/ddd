package cc.mikaka.ddd.service.impl;

import cc.mikaka.ddd.bean.dto.UserDTO;
import cc.mikaka.ddd.bean.request.user.BatchQueryUserRequest;
import cc.mikaka.ddd.bean.request.user.CreateUserRequest;
import cc.mikaka.ddd.bean.request.user.EditUserRequest;
import cc.mikaka.ddd.bean.request.user.UserIdRequest;
import cc.mikaka.ddd.bean.result.PageList;
import cc.mikaka.ddd.common.enums.ActionType;
import cc.mikaka.ddd.common.enums.BizType;
import cc.mikaka.ddd.core.model.UserModel;
import cc.mikaka.ddd.core.repository.UserRepository;
import cc.mikaka.ddd.core.repository.condition.UserQueryCondition;
import cc.mikaka.ddd.service.BaseBizService;
import cc.mikaka.ddd.service.UserService;
import cc.mikaka.ddd.service.convertor.UserConvert;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl extends BaseBizService implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserConvert userConvert;

    @Override
    public String create(CreateUserRequest request) {
        return execute(request, BizType.USER, ActionType.CREATE);
    }

    @Override
    public Void edit(EditUserRequest request) {
        return execute(request, BizType.USER, ActionType.EDIT);
    }

    @Override
    public Void delete(UserIdRequest request) {
        return execute(request, BizType.USER, ActionType.DELETE);
    }

    @Override
    public Void enable(UserIdRequest request) {
        return execute(request, BizType.USER, ActionType.ENABLE);
    }

    @Override
    public Void disable(UserIdRequest request) {
        return execute(request, BizType.USER, ActionType.DISABLE);
    }

    @Override
    public UserDTO getById(UserIdRequest request) {
        UserModel userModel = userRepository.queryByUserId(request.getUserId());
        return userConvert.model2Dto(userModel);
    }

    @Override
    public List<UserDTO> batchQuery(BatchQueryUserRequest request) {
        UserQueryCondition condition = buildUserQueryCondition(request);

        List<UserModel> userModels = userRepository.queryList(condition);
        return userConvert.model2Dtos(userModels);
    }


    @Override
    public PageList<UserDTO> pageQuery(BatchQueryUserRequest request) {
        UserQueryCondition condition = buildUserQueryCondition(request);

        int count = userRepository.countByCondition(condition);
        if (count == 0) {
            return PageList.createPageData(Lists.newArrayList(), count);
        }

        List<UserModel> userModels = userRepository.queryPage(condition);
        return PageList.createPageData(userConvert.model2Dtos(userModels), 0);
    }

    private UserQueryCondition buildUserQueryCondition(BatchQueryUserRequest request) {
        UserQueryCondition condition = new UserQueryCondition();
        condition.setNameLike(request.getNameLike());

        if (CollectionUtils.isNotEmpty(request.getUserIds())) {
            condition.setUserIds(request.getUserIds());
        }

        condition.setPageIndex(request.getPageIndex());
        condition.setPageSize(request.getPageSize());
        return condition;
    }
}
