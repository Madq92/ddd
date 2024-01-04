package cc.mikaka.ddd.client.service;

import cc.mikaka.ddd.bean.dto.UserDTO;
import cc.mikaka.ddd.bean.request.user.BatchQueryUserRequest;
import cc.mikaka.ddd.bean.request.user.UserIdRequest;
import cc.mikaka.ddd.bean.result.CommonResult;
import cc.mikaka.ddd.bean.result.PageList;

import java.util.List;

public interface UserQueryClient {

    /**
     * 主键查询
     *
     * @param request
     * @return
     */
    CommonResult<UserDTO> queryById(UserIdRequest request);

    /**
     * 列表查询
     *
     * @param request
     */
    CommonResult<List<UserDTO>> batchQuery(BatchQueryUserRequest request);


    /**
     * 分页查询
     *
     * @param request
     */
    CommonResult<PageList<UserDTO>> pageQuery(BatchQueryUserRequest request);
}
