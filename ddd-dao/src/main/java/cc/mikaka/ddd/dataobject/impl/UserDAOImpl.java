package cc.mikaka.ddd.dataobject.impl;

import cc.mikaka.ddd.dao.UserDO;
import cc.mikaka.ddd.dataobject.UserDAO;
import org.springframework.stereotype.Component;

@Component
public class UserDAOImpl implements UserDAO {
    @Override
    public int deleteByPrimaryKey(String userId) {
        return 0;
    }

    @Override
    public int insert(UserDO record) {
        return 0;
    }

    @Override
    public UserDO selectByPrimaryKey(String userId) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(UserDO record) {
        return 0;
    }
}
