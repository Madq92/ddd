package cc.mikaka.ddd.core.convertor.mapstruct;

import cc.mikaka.ddd.core.model.UserModel;
import cc.mikaka.ddd.dao.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserCoreMapsConvert {
    UserCoreMapsConvert INSTANCE = Mappers.getMapper(UserCoreMapsConvert.class);

    UserModel do2Model(UserDO source);

    UserDO model2Do(UserModel source);
}
