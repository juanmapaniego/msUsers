package com.globallogic.jmpaniego.msusers.mapper;

import com.globallogic.jmpaniego.msusers.model.Phone;
import com.globallogic.jmpaniego.msusers.model.dto.PhoneDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhoneMapper {
    PhoneDTO phoneToPhoneDto(Phone phone);
    @Mapping(target = "id", expression = "java(null)")
    Phone phoneDtoToPhone(PhoneDTO phone);
}
