package cc.mikaka.ddd.client.impl;

import cc.mikaka.ddd.bean.dto.UserDTO;
import cc.mikaka.ddd.bean.request.user.BatchQueryUserRequest;
import cc.mikaka.ddd.bean.request.user.CreateUserRequest;
import cc.mikaka.ddd.bean.request.user.EditUserRequest;
import cc.mikaka.ddd.bean.request.user.UserIdRequest;
import cc.mikaka.ddd.bean.result.CommonResult;
import cc.mikaka.ddd.bean.result.PageList;
import cc.mikaka.ddd.client.service.UserManageClient;
import cc.mikaka.ddd.client.service.UserQueryClient;
import cc.mikaka.ddd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserClientImpl implements UserQueryClient, UserManageClient {
    @Autowired
    UserService userService;

    @Override
    public CommonResult<UserDTO> queryById(UserIdRequest request) {
        return CommonResult.createSucc(userService.getById(request));
    }

    @Override
    public CommonResult<List<UserDTO>> batchQuery(BatchQueryUserRequest request) {
        return CommonResult.createSucc(userService.batchQuery(request));
    }

    @Override
    public CommonResult<PageList<UserDTO>> pageQuery(BatchQueryUserRequest request) {
        return CommonResult.createSucc(userService.pageQuery(request));
    }

    @Override
    public CommonResult<String> create(CreateUserRequest request) {
        return CommonResult.createSucc(userService.create(request));
    }

    @Override
    public CommonResult<Boolean> edit(EditUserRequest request) {
        return CommonResult.createSucc(userService.edit(request));
    }

    @Override
    public CommonResult<Boolean> delete(UserIdRequest request) {
        return CommonResult.createSucc(userService.delete(request));
    }

    @Override
    public CommonResult<Boolean> enable(UserIdRequest request) {
        return CommonResult.createSucc(userService.enable(request));
    }

    @Override
    public CommonResult<Boolean> disable(UserIdRequest request) {
        return CommonResult.createSucc(userService.disable(request));
    }
}
