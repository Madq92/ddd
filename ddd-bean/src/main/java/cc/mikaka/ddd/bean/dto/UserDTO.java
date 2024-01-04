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
    private String userName;
    @Schema(title = "用户年龄")
    private Integer age;
    @Schema(title = "用户状态")
    private String status;
}
