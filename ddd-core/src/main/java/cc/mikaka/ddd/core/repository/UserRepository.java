package cc.mikaka.ddd.core.repository;


import cc.mikaka.ddd.core.model.UserModel;
import cc.mikaka.ddd.core.repository.condition.UserQueryCondition;

import java.util.List;

public interface UserRepository {
    void insert(UserModel userModel);

    void update(UserModel userModel);

    void updateStatus(String userId, String status);

    UserModel queryByUserId(String userId);

    List<UserModel> queryList(UserQueryCondition condition);

    List<UserModel> queryPage(UserQueryCondition condition);

    int countByCondition(UserQueryCondition condition);
}
