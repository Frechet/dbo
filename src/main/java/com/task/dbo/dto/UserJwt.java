package com.task.dbo.dto;

import java.util.ArrayList;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
@ToString
public class UserJwt extends User implements Serializable {

  private Long id;

  public UserJwt(Long id, String username, String password) {
    super(username, password, new ArrayList<>());
    this.id = id;
  }
}