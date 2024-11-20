package com.task.dbo.dto;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto implements Serializable {
  private Long id;
  private String name;
  private LocalDate birthdate;
  private String password;

}