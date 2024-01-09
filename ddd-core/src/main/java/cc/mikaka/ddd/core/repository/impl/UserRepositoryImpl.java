package cc.mikaka.ddd.core.repository.impl;

import cc.mikaka.ddd.core.convertor.UserCoreConvert;
import cc.mikaka.ddd.core.model.UserModel;
import cc.mikaka.ddd.core.repository.UserRepository;
import cc.mikaka.ddd.core.repository.condition.UserQueryCondition;
import cc.mikaka.ddd.dataobject.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserCoreConvert userCoreConvert;

    @Override
    public UserModel queryByUserId(String userId) {
        return userCoreConvert.do2Model(userDAO.selectByPrimaryKey(userId));
    }

    @Override
    public void insert(UserModel userModel) {
        userDAO.insert(userCoreConvert.model2Do(userModel));
    }

    @Override
    public void update(UserModel userModel) {
        userDAO.updateByPrimaryKeySelective(userCoreConvert.model2Do(userModel));
    }

    @Override
    public void updateStatus(String userId, String status) {
        UserModel updateModel = new UserModel();
        updateModel.setUserId(userId);
        updateModel.setStatus(status);
        userDAO.updateByPrimaryKeySelective(userCoreConvert.model2Do(updateModel));
    }

    @Override
    public List<UserModel> queryList(UserQueryCondition condition) {
        return null;
    }

    @Override
    public List<UserModel> queryPage(UserQueryCondition condition) {
        return null;
    }

    @Override
    public int countByCondition(UserQueryCondition condition) {
        return 0;
    }
}
