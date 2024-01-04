package cc.mikaka.ddd.service.impl;

import cc.mikaka.ddd.bean.dto.UserDTO;
import cc.mikaka.ddd.bean.request.user.BatchQueryUserRequest;
import cc.mikaka.ddd.bean.request.user.CreateUserRequest;
import cc.mikaka.ddd.bean.request.user.ModifyUserRequest;
import cc.mikaka.ddd.bean.request.user.UserIdRequest;
import cc.mikaka.ddd.bean.result.PageList;
import cc.mikaka.ddd.service.UserService;
import cc.mikaka.ddd.service.base.BaseBizService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl extends BaseBizService implements UserService {

    @Override
    public UserDTO queryById(UserIdRequest request) {
        String userId = request.getUserId();

        UserDTO userDTO= new UserDTO();
        userDTO.setUserId(userId);
        return userDTO;
    }

    @Override
    public List<UserDTO> batchQuery(BatchQueryUserRequest request) {
        return null;
    }

    @Override
    public PageList<UserDTO> pageQuery(BatchQueryUserRequest request) {
        return null;
    }

    @Override
    public String create(CreateUserRequest request) {
        return null;
    }

    @Override
    public Void modify(ModifyUserRequest request) {
        return null;
    }

    @Override
    public Void delete(UserIdRequest request) {
        return null;
    }

    @Override
    public Void enable(UserIdRequest request) {
        return null;
    }

    @Override
    public Void disable(UserIdRequest request) {
        return null;
    }


}
