package com.task.dbo.mapper;

import com.task.dbo.domain.Client;
import com.task.dbo.dto.ClientDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ClientMapper {
  ClientDto toDto(Client client);
  List<ClientDto> toDto(List<Client> clients);

}
