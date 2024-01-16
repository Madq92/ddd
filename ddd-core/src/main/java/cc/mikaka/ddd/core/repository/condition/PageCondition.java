package cc.mikaka.ddd.core.repository.condition;

import lombok.Data;

import java.util.List;

@Data
public class PageCondition {
    /**
     * 当前请求页码
     */
    private Integer pageIndex;

    /**
     * 每页数据条数
     */
    private Integer pageSize;

    /**
     * 排序
     */
    private List<String> orders;
}
