package cc.mikaka.ddd.core.convertor;

import cc.mikaka.ddd.core.convertor.mapstruct.UserCoreMapsConvert;
import cc.mikaka.ddd.core.model.UserModel;
import cc.mikaka.ddd.dao.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCoreConvert {
    @Autowired
    UserCoreMapsConvert userCoreMapsConvert;

    public UserModel do2Model(UserDO userDO) {
        return userCoreMapsConvert.do2Model(userDO);
    }

    public UserDO model2Do(UserModel userModel) {
        return userCoreMapsConvert.model2Do(userModel);
    }
}
