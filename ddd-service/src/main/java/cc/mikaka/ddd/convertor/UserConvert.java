package cc.mikaka.ddd.convertor;

import cc.mikaka.ddd.bean.dto.UserDTO;
import cc.mikaka.ddd.convertor.mapstruct.UserCoreConvert;
import cc.mikaka.ddd.dao.UserDO;
import cc.mikaka.ddd.model.user.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserConvert {
    public UserModel do2Model(UserDO userDO) {
        return UserCoreConvert.INSTANCE.do2Model(userDO);
    }

    public UserDO model2Do(UserModel userModel) {
        return UserCoreConvert.INSTANCE.model2Do(userModel);
    }

    public UserModel dto2Model(UserDTO userDTO) {
        return UserCoreConvert.INSTANCE.dto2Model(userDTO);
    }

    public UserDTO model2Dto(UserModel userModel) {
        return UserCoreConvert.INSTANCE.model2Dto(userModel);
    }
}
