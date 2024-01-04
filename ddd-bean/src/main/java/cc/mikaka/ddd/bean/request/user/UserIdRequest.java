package cc.mikaka.ddd.bean.request.user;

import cc.mikaka.ddd.bean.request.BaseRequest;
import lombok.Data;

@Data
public class UserIdRequest extends BaseRequest {
    private String userId;
}
