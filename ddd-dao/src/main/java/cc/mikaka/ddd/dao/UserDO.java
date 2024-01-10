package cc.mikaka.ddd.dao;

import lombok.Data;

/**
 * 与数据库表一一对应
 */
@Data
public class UserDO {
    private String userId;
    private String userName;
    private Integer age;
    private String status;
}
