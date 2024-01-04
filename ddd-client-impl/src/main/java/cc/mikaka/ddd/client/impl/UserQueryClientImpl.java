package cc.mikaka.ddd.client.impl;

import cc.mikaka.ddd.bean.dto.UserDTO;
import cc.mikaka.ddd.bean.request.user.BatchQueryUserRequest;
import cc.mikaka.ddd.bean.request.user.UserIdRequest;
import cc.mikaka.ddd.bean.result.CommonResult;
import cc.mikaka.ddd.bean.result.PageList;
import cc.mikaka.ddd.client.service.UserQueryClient;
import cc.mikaka.ddd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserQueryClientImpl implements UserQueryClient {
    @Autowired
    UserService userService;

    @Override
    public CommonResult<UserDTO> queryById(UserIdRequest request) {
        return CommonResult.createSucc(userService.queryById(request));
    }

    @Override
    public CommonResult<List<UserDTO>> batchQuery(BatchQueryUserRequest request) {
        return CommonResult.createSucc(userService.batchQuery(request));
    }

    @Override
    public CommonResult<PageList<UserDTO>> pageQuery(BatchQueryUserRequest request) {
        return CommonResult.createSucc(userService.pageQuery(request));
    }
}
