package cc.mikaka.ddd.core.repository.condition;

import lombok.Data;

@Data
public class BasePageCondition {
    private Integer pageIndex;
    private Integer pageSize;
}
