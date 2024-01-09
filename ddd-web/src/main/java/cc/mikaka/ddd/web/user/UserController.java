package cc.mikaka.ddd.web.user;


import cc.mikaka.ddd.bean.dto.UserDTO;
import cc.mikaka.ddd.bean.request.user.BatchQueryUserRequest;
import cc.mikaka.ddd.bean.request.user.CreateUserRequest;
import cc.mikaka.ddd.bean.request.user.EditUserRequest;
import cc.mikaka.ddd.bean.request.user.UserIdRequest;
import cc.mikaka.ddd.bean.result.CommonResult;
import cc.mikaka.ddd.bean.result.PageList;
import cc.mikaka.ddd.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "User获取ById")
    @GetMapping("/getById")
    public CommonResult<UserDTO> getById(@RequestParam("userId") @Parameter(description = "用户ID") String userId) {
        UserIdRequest request = new UserIdRequest();
        request.setUserId(userId);
        return CommonResult.createSucc(userService.getById(request));
    }

    @Operation(summary = "User列表")
    @GetMapping("/list")
    public CommonResult<List<UserDTO>> batchQuery(@RequestParam("userIds") @Parameter(description = "用户ID列表") List<String> userIds,
                                                  @RequestParam("nameLike") @Parameter(description = "用户姓名模糊查询") String nameLike) {
        BatchQueryUserRequest request = new BatchQueryUserRequest();
        request.setUserIds(userIds);
        request.setNameLike(nameLike);
        return CommonResult.createSucc(userService.batchQuery(request));
    }

    @Operation(summary = "User分页")
    @GetMapping("/page")
    public CommonResult<PageList<UserDTO>> pageQuery(@RequestParam("userIds") @Parameter(description = "用户ID列表") List<String> userIds,
                                                     @RequestParam("userNameLike") @Parameter(description = "用户姓名模糊查询") String nameLike,
                                                     @RequestParam("pageIndex") @Parameter(description = "分页Index") Integer pageIndex,
                                                     @RequestParam("pageSize") @Parameter(description = "分页大小") Integer pageSize) {
        BatchQueryUserRequest request = new BatchQueryUserRequest();
        request.setUserIds(userIds);
        request.setNameLike(nameLike);
        request.setPageIndex(pageIndex);
        request.setPageSize(pageSize);
        return CommonResult.createSucc(userService.pageQuery(request));
    }

    @Operation(summary = "User创建")
    @PostMapping("/create")
    public CommonResult<String> create(@RequestBody CreateUserRequest request) {
        return CommonResult.createSucc(userService.create(request));
    }

    @Operation(summary = "User编辑")
    @PutMapping("/edit")
    public CommonResult<Boolean> edit(@RequestBody EditUserRequest request) {
        userService.edit(request);
        return CommonResult.createSucc(true);
    }

    @Operation(summary = "User删除")
    @PutMapping("/delete")
    public CommonResult<Boolean> delete(@RequestBody UserIdRequest request) {
        userService.delete(request);
        return CommonResult.createSucc(true);
    }

    @Operation(summary = "User启用")
    @PutMapping("/enable")
    public CommonResult<Boolean> enable(@RequestBody UserIdRequest request) {
        userService.enable(request);
        return CommonResult.createSucc(true);
    }

    @Operation(summary = "User停用")
    @PutMapping("/disable")
    public CommonResult<Boolean> disable(@RequestBody UserIdRequest request) {
        userService.disable(request);
        return CommonResult.createSucc(true);
    }
}
