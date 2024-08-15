package cc.mikaka.ddd.core.convertor.mapstruct;

import cc.mikaka.ddd.common.convert.BaseModelConvert;
import cc.mikaka.ddd.core.model.UserModel;
import cc.mikaka.ddd.dao.UserDO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserCoreMapsConvert extends BaseModelConvert<UserModel, UserDO> {

}
