package cc.mikaka.ddd.dataobject;

import cc.mikaka.ddd.condition.BaseCondition;
import cc.mikaka.ddd.dao.UserDO;
import cc.mikaka.ddd.dao.UserDOExample;
import cc.mikaka.ddd.plugins.Condition;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Database Table Remarks:
 *   系统用户
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table user
 *
 * @mbg.generated do_not_delete_during_merge
 */
@Mapper
public interface UserDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    long countByExample(UserDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    int deleteByExample(UserDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(@Param("userId") String userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    int insert(UserDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    List<UserDO> selectByExample(UserDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    UserDO selectByPrimaryKey(@Param("userId") String userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") UserDO record, @Param("example") UserDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") UserDO record, @Param("example") UserDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UserDO record);

    /**
     * This method was generated by MyBatis Generator.
     * 【merchantId 必输】
     *
     * @mbg.generated do_not_delete_during_merge
     */
    List<UserDO> listByCondition(BaseCondition baseCondition);

    /**
     * This method was generated by MyBatis Generator.
     * 【merchantId 必输】
     *
     * @mbg.generated do_not_delete_during_merge
     */
    List<UserDO> pageListByCondition(BaseCondition baseCondition);

    /**
     * This method was generated by MyBatis Generator.
     * 【merchantId 必输】
     *
     * @mbg.generated do_not_delete_during_merge
     */
    int countByCondition(BaseCondition baseCondition);
}