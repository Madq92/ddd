package cc.mikaka.ddd.model.user;

import lombok.Data;

@Data
public class UserModel {

    private String userId;
    private String userName;
    private Integer age;
    private String status;
}
