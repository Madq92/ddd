package cc.mikaka.ddd.dataobject;

import cc.mikaka.ddd.dao.UserDO;
import org.apache.ibatis.annotations.Param;

public interface UserDAO {
    int deleteByPrimaryKey(@Param("userId") String userId);

    int insert(UserDO record);

    UserDO selectByPrimaryKey(@Param("userId") String userId);

    int updateByPrimaryKeySelective(UserDO record);
}
