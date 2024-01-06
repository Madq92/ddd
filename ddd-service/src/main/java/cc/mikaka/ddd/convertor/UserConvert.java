package cc.mikaka.ddd.convertor;

import cc.mikaka.ddd.bean.dto.UserDTO;
import cc.mikaka.ddd.convertor.mapstruct.UserMapsConvert;
import cc.mikaka.ddd.core.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserConvert {

    public UserModel dto2Model(UserDTO userDTO) {
        return UserMapsConvert.INSTANCE.dto2Model(userDTO);
    }

    public UserDTO model2Dto(UserModel userModel) {
        return UserMapsConvert.INSTANCE.model2Dto(userModel);
    }
}
