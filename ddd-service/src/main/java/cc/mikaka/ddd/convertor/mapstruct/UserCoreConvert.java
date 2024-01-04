package cc.mikaka.ddd.convertor.mapstruct;

import cc.mikaka.ddd.bean.dto.UserDTO;
import cc.mikaka.ddd.dao.UserDO;
import cc.mikaka.ddd.model.user.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserCoreConvert {
    UserCoreConvert INSTANCE = Mappers.getMapper(UserCoreConvert.class);

    UserModel do2Model(UserDO source);

    UserDO model2Do(UserModel source);

    UserModel dto2Model(UserDTO source);

    UserDTO model2Dto(UserModel source);
}
