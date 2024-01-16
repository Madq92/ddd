package cc.mikaka.ddd.core.repository.impl;

import cc.mikaka.ddd.bean.enums.OrderRuleEnum;
import cc.mikaka.ddd.common.util.AssertUtil;
import cc.mikaka.ddd.core.convertor.UserCoreConvert;
import cc.mikaka.ddd.core.model.UserModel;
import cc.mikaka.ddd.core.repository.UserRepository;
import cc.mikaka.ddd.core.repository.condition.UserQueryCondition;
import cc.mikaka.ddd.dao.UserDO;
import cc.mikaka.ddd.dao.UserDOExample;
import cc.mikaka.ddd.dataobject.UserDAO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static cc.mikaka.ddd.common.util.ConditionUtil.addCondition;

@Component
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserCoreConvert userCoreConvert;


    @Override
    public UserModel queryByUserId(String userId) {
        AssertUtil.notNull(userId, "业务主键不能为空");
        return userCoreConvert.do2Model(userDAO.selectByPrimaryKey(userId));
    }

    @Override
    public void insert(UserModel userModel) {
        AssertUtil.notNull(userModel.getUserId(), "业务主键不能为空");
        userDAO.insert(userCoreConvert.model2Do(userModel));
    }

    @Override
    public void update(UserModel userModel) {
        AssertUtil.notNull(userModel.getUserId(), "业务主键不能为空");
        userDAO.updateByPrimaryKeySelective(userCoreConvert.model2Do(userModel));
    }

    @Override
    public void updateStatus(String userId, String status) {
        AssertUtil.notNull(userId, "业务主键不能为空");
        UserModel updateModel = new UserModel();
        updateModel.setUserId(userId);
        updateModel.setStatus(status);
        userDAO.updateByPrimaryKeySelective(userCoreConvert.model2Do(updateModel));
    }

    @Override
    public List<UserModel> queryList(UserQueryCondition condition) {
        //构建查询条件
        UserDOExample example = buildExample(condition);

        //执行查询
        List<UserDO> userDOS = userDAO.selectByExample(example);

        //模型转换
        return userDOS.stream().map(userCoreConvert::do2Model).collect(Collectors.toList());
    }

    @Override
    public List<UserModel> queryPage(UserQueryCondition condition) {
        //构建查询条件
        UserDOExample example = buildExample(condition);

        //执行查询
        List<UserDO> userDOS = userDAO.selectByExample(example);

        //模型转换
        return userDOS.stream().map(userCoreConvert::do2Model).collect(Collectors.toList());
    }

    @Override
    public int countByCondition(UserQueryCondition condition) {
        //构建查询条件
        UserDOExample example = buildExample(condition);

        //执行查询
        return (int) userDAO.countByExample(example);
    }

    private UserDOExample buildExample(UserQueryCondition condition) {
        UserDOExample example = new UserDOExample();

        //查询条件
        UserDOExample.Criteria or = example.or();
        addCondition(or::andUserIdEqualTo, condition.getUserId());
        addCondition(or::andUserIdIn, condition.getUserIds());
        addCondition(or::andNameLike, condition.getNameLike());
        addCondition(or::andNameEqualTo, condition.getName());

        //分页
        if (null != condition.getPageIndex() && null != condition.getPageSize()) {
            example.page(condition.getPageIndex() - 1, condition.getPageSize());
        }

        //排序
        if (CollectionUtils.isNotEmpty(condition.getOrders())) {
            String orderByClause = condition.getOrders().stream().collect(Collectors.joining(" , "));
            example.setOrderByClause(orderByClause);
        } else {
            example.setOrderByClause(OrderRuleEnum.ID_DESC.getOrderCase());
        }

        return example;
    }
}
