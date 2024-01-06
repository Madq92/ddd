package cc.mikaka.ddd.core.repository;


import cc.mikaka.ddd.core.model.UserModel;
import cc.mikaka.ddd.core.repository.condition.UserQueryCondition;

import java.util.List;

public interface UserRepository {
    UserModel queryByUserId(String userId);

    void insert(UserModel userModel);

    void update(UserModel userModel);

    List<UserModel> queryList(UserQueryCondition condition);

}
