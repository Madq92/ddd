package cc.mikaka.ddd.bean.result;


import cc.mikaka.ddd.bean.ToString;

import java.util.List;


/**
 * 批量数据
 */
public class PageList<T> extends ToString {

    private static final long serialVersionUID = 8683452581122892181L;

    /**
     * 总记录数
     */
    private int totalSize;

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

    /**
     * 创建分页数据
     */
    public static <T> PageList<T> createPageData(List<T> values, int totalSize) {
        return new PageList<T>(values, totalSize);
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }
}
