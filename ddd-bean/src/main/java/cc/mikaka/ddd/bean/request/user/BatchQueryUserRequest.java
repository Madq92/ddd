package cc.mikaka.ddd.bean.request.user;

import cc.mikaka.ddd.bean.request.BasePageQueryRequest;
import lombok.Data;

import java.util.List;

@Data
public class BatchQueryUserRequest extends BasePageQueryRequest {

    private List<String> userIds;
    private String nameLike;
}
