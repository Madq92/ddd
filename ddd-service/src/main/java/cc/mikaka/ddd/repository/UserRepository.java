package cc.mikaka.ddd.repository;

import java.util.List;
import cc.mikaka.ddd.model.user.UserModel;
import cc.mikaka.ddd.repository.condition.UserQueryCondition;

public interface UserRepository {

    UserModel queryByUserId(String userId);

    void insert(UserModel userModel);

    void update(UserModel userModel);

    List<UserModel> queryList(UserQueryCondition condition);
}
