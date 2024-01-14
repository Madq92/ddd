package cc.mikaka.ddd.core.domain;

import cc.mikaka.ddd.core.model.UserModel;

public class UserDomain {
    private UserModel userModel;

    public String createUser() {
        return userModel.getUserId();
    }

    public String editUser() {
        return userModel.getUserId();
    }

    public String deleteUser() {
        return userModel.getUserId();
    }

    public String enableUser() {
        return userModel.getUserId();
    }

    public String disableUser() {
        return userModel.getUserId();
    }
}
