package cc.mikaka.ddd.core.convertor;

import cc.mikaka.ddd.core.convertor.mapstruct.UserCoreMapsConvert;
import cc.mikaka.ddd.core.model.UserModel;
import cc.mikaka.ddd.dao.UserDO;
import org.springframework.stereotype.Component;

@Component
public class UserCoreConvert {
    public UserModel do2Model(UserDO userDO) {
        return UserCoreMapsConvert.INSTANCE.do2Model(userDO);
    }

    public UserDO model2Do(UserModel userModel) {
        return UserCoreMapsConvert.INSTANCE.model2Do(userModel);
    }
}
