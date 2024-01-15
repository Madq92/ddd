package cc.mikaka.ddd.dao;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDO {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 创建时间
     */
    private LocalDateTime serverCreateTime;

    /**
     * 更新时间
     */
    private LocalDateTime serverUpdateTime;
}
