package cc.mikaka.ddd.dataobject.impl;

import cc.mikaka.ddd.dao.UserDO;
import cc.mikaka.ddd.dataobject.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserDAOImpl implements UserDAO {
    @Override
    public int deleteByPrimaryKey(String userId) {
        log.info("UserDAO deleteByPrimaryKey : {}", userId);
        return 1;
    }

    @Override
    public int insert(UserDO record) {
        log.info("UserDAO insert : {}", record);
        return 1;
    }

    @Override
    public UserDO selectByPrimaryKey(String userId) {
        log.info("UserDAO selectByPrimaryKey : {}", userId);
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(UserDO record) {
        log.info("UserDAO updateByPrimaryKeySelective : {}", record);
        return 1;
    }
}
