package cc.mikaka.ddd.core.repository.condition;

import lombok.Data;

import java.util.List;

@Data
public class UserQueryCondition extends PageCondition {
    private String userId;
    private List<String> userIds;
    private String nameLike;
    private String name;
}
