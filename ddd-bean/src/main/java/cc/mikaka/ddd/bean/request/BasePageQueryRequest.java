package cc.mikaka.ddd.bean.request;

import cc.mikaka.ddd.bean.enums.OrderRuleEnum;
import lombok.Data;

import java.util.List;


/**
 * 分页请求参数
 */
@Data
abstract public class BasePageQueryRequest extends BaseRequest {

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
    private List<OrderRuleEnum> orderRules;
}
