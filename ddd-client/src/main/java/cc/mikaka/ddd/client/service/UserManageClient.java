package cc.mikaka.ddd.client.service;

import cc.mikaka.ddd.bean.request.user.CreateUserRequest;
import cc.mikaka.ddd.bean.request.user.EditUserRequest;
import cc.mikaka.ddd.bean.request.user.UserIdRequest;
import cc.mikaka.ddd.bean.result.CommonResult;

public interface UserManageClient {

    /**
     * 创建用户
     *
     * @param request
     * @return
     */
    CommonResult<String> create(CreateUserRequest request);

    /**
     * 编辑用户
     *
     * @param request
     * @return
     */
    CommonResult<Void> modify(EditUserRequest request);

    /**
     * 删除用户
     *
     * @param request
     * @return
     */
    CommonResult<Void> delete(UserIdRequest request);

    /**
     * 启用用户
     *
     * @param request
     * @return
     */
    CommonResult<Void> enable(UserIdRequest request);

    /**
     * 停用用户
     *
     * @param request
     * @return
     */
    CommonResult<Void> disable(UserIdRequest request);
}
