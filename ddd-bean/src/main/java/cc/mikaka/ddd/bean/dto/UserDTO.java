package cc.mikaka.ddd.bean.dto;

import cc.mikaka.ddd.bean.ToString;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class UserDTO extends ToString {
    @Schema(title = "用户ID")
    private String userId;
    @Schema(title = "用户Name")
    private String name;
    @Schema(title = "电话")
    private String phone;
    @Schema(title = "头像")
    private String avatar;
    @Schema(title = "用户状态")
    private String status;
}
