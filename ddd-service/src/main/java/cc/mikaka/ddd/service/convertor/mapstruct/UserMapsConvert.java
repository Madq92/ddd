package cc.mikaka.ddd.service.convertor.mapstruct;

import cc.mikaka.ddd.bean.dto.UserDTO;
import cc.mikaka.ddd.core.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapsConvert {
    UserMapsConvert INSTANCE = Mappers.getMapper(UserMapsConvert.class);

    UserModel dto2Model(UserDTO source);

    UserDTO model2Dto(UserModel source);
}
