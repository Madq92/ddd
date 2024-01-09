package cc.mikaka.ddd.bean.request.user;

import cc.mikaka.ddd.bean.dto.UserDTO;
import cc.mikaka.ddd.bean.request.BaseRequest;
import lombok.Data;

@Data
public class EditUserRequest extends BaseRequest {
    private UserDTO user;
}
