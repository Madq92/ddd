package cc.mikaka.ddd.bean.request.user;

import java.util.List;
import cc.mikaka.ddd.bean.request.BasePageQueryRequest;
import lombok.Data;

@Data
public class BatchQueryUserRequest extends BasePageQueryRequest {

    private List<String> userIds;
    private String userNameLike;
}
