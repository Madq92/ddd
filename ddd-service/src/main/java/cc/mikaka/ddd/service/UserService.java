package cc.mikaka.ddd.service;

import cc.mikaka.ddd.bean.dto.UserDTO;
import cc.mikaka.ddd.bean.request.user.BatchQueryUserRequest;
import cc.mikaka.ddd.bean.request.user.CreateUserRequest;
import cc.mikaka.ddd.bean.request.user.EditUserRequest;
import cc.mikaka.ddd.bean.request.user.UserIdRequest;
import cc.mikaka.ddd.bean.result.PageList;

import java.util.List;

public interface UserService {
    /**
     * 创建用户
     *
     * @param request
     * @return
     */
    String create(CreateUserRequest request);

    /**
     * 编辑用户
     *
     * @param request
     * @return
     */
    Boolean edit(EditUserRequest request);

    /**
     * 删除用户
     *
     * @param request
     * @return
     */
    Boolean delete(UserIdRequest request);

    /**
     * 启用用户
     *
     * @param request
     * @return
     */
    Boolean enable(UserIdRequest request);

    /**
     * 停用用户
     *
     * @param request
     * @return
     */
    Boolean disable(UserIdRequest request);

    /**
     * 单个查询
     *
     * @param request
     * @return
     */
    UserDTO getById(UserIdRequest request);

    /**
     * 批量查询
     *
     * @param request
     * @return
     */
    List<UserDTO> batchQuery(BatchQueryUserRequest request);

    /**
     * 分页查询
     *
     * @param request
     * @return
     */
    PageList<UserDTO> pageQuery(BatchQueryUserRequest request);


}
