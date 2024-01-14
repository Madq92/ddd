package cc.mikaka.ddd.core.repository.condition;

import cc.mikaka.ddd.condition.BaseCondition;
import lombok.Data;

import java.util.List;

@Data
public class UserQueryCondition extends BaseCondition {
    private String userId;
    private List<String> userIds;
    private String nameLike;
    private String name;
}
