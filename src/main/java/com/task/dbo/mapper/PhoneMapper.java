package com.task.dbo.mapper;

import com.task.dbo.domain.Phone;
import com.task.dbo.dto.PhoneDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhoneMapper {
  PhoneDto toDto(Phone phone);
  List<PhoneDto> toDto(List<Phone> phones);

}
