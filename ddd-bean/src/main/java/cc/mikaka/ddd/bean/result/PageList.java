package cc.mikaka.ddd.bean.result;


import cc.mikaka.ddd.bean.ToString;
import lombok.Data;

import java.util.List;


/**
 * 分页数据
 */
@Data
public class PageList<T> extends ToString {
    /**
     * 总数
     */
    private int totalSize;

    /**
     * 当前页
     */
    private int pageIndex;

    /**
     * 每页数量
     */
    private int pageSize;

    /**
     * 数据
     */
    private List<T> values;

    public PageList() {
    }

    public PageList(List<T> values, int totalSize) {
        this.totalSize = totalSize;
        this.values = values;
    }

    public static <T> PageList<T> createPageData(List<T> values, int totalSize) {
        return new PageList<T>(values, totalSize);
    }
}
