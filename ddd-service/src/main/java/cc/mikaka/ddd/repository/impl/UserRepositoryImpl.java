package cc.mikaka.ddd.repository.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cc.mikaka.ddd.convertor.UserConvert;
import cc.mikaka.ddd.dataobject.UserDAO;
import cc.mikaka.ddd.model.user.UserModel;
import cc.mikaka.ddd.repository.UserRepository;
import cc.mikaka.ddd.repository.condition.UserQueryCondition;

@Component
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    UserDAO userDAO;
    @Autowired
    UserConvert userConvert;

    @Override
    public UserModel queryByUserId(String userId) {
        return userConvert.do2Model(userDAO.selectByPrimaryKey(userId));
    }

    @Override
    public void insert(UserModel userModel) {
        userDAO.insert(userConvert.model2Do(userModel));
    }

    @Override
    public void update(UserModel userModel) {
        userDAO.updateByPrimaryKeySelective(userConvert.model2Do(userModel));
    }

    @Override
    public List<UserModel> queryList(UserQueryCondition condition) {
        return null;
    }
}
