package com.task.dbo.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.task.dbo.domain.Client;
import com.task.dbo.dto.ClientDto;
import org.junit.jupiter.api.Test;

class ClientMapperTest {

  @Test
  void testToDto_Success() {
    Client client =  new Client();
    client.setName("Ivan");
    ClientDto expected = new ClientDto();
    expected.setName("Ivan");

    ClientDto actual = new ClientMapperImpl().toDto(client);
    assertEquals(expected.getName(), actual.getName());
  }
}