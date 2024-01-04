package cc.mikaka.ddd.bean.request;

import cc.mikaka.ddd.bean.enums.OrderRuleEnum;

import java.util.List;


/**
 * 分页请求参数
 */
abstract public class BasePageQueryRequest extends BaseRequest{

    /**
     * 当前请求页码
     */
    private int pageIndex = 1;

    /**
     * 每页数据条数
     */
    private int pageSize = 10;

    /**
     * 排序
     */
    private List<OrderRuleEnum> orderRules;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<OrderRuleEnum> getOrderRules() {
        return orderRules;
    }

    public void setOrderRules(List<OrderRuleEnum> orderRules) {
        this.orderRules = orderRules;
    }
}
