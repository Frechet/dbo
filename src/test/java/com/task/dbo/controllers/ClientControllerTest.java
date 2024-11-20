package com.task.dbo.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.task.dbo.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
class ClientControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  @MockBean
  private JwtUtil jwtUtil;

  @Sql("classpath:db/migration/V2__init_data.sql")
  @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  @Test
  void testTransferMoney_Success() throws Exception {
    Mockito.when(jwtUtil.extractUserId()).thenReturn(1L);

    MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    mockMvc.perform(put("/api/client/transfermoney")
            .param("transferTo", String.valueOf(2L))
            .param("value", String.valueOf(10.5))
        )
        .andExpect(status().isOk());
  }

  @Sql("classpath:db/migration/V2__init_data.sql")
  @Sql(scripts = "classpath:clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
  @Test
  void testTransferMoney_FailNotEnoughMoney() throws Exception {
    Mockito.when(jwtUtil.extractUserId()).thenReturn(1L);

    MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    mockMvc.perform(put("/api/client/transfermoney")
            .param("transferTo", String.valueOf(2L))
            .param("value", String.valueOf(1_000_000.5))
        )
        .andExpect(status().isBadRequest());
  }
}