package cc.mikaka.ddd.service.convertor;

import cc.mikaka.ddd.bean.dto.UserDTO;
import cc.mikaka.ddd.core.model.UserModel;
import cc.mikaka.ddd.service.convertor.mapstruct.UserMapsConvert;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConvert {

    public UserModel dto2Model(UserDTO userDTO) {
        return UserMapsConvert.INSTANCE.dto2Model(userDTO);
    }

    public UserDTO model2Dto(UserModel userModel) {
        return UserMapsConvert.INSTANCE.model2Dto(userModel);
    }

    public List<UserDTO> model2Dtos(List<UserModel> userModels) {
        if (CollectionUtils.isEmpty(userModels)) {
            return Lists.newArrayList();
        }

        return userModels.stream().map(this::model2Dto).collect(Collectors.toList());
    }
}
