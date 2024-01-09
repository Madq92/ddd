package cc.mikaka.ddd.core.model;

import lombok.Data;

@Data
public class UserModel {
    private String userId;
    private String name;
    private String phone;
    private String avatar;
    private String status;
}
