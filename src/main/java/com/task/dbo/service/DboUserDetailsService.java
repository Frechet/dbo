package com.task.dbo.service;

import com.task.dbo.dto.ClientDto;
import com.task.dbo.dto.UserJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DboUserDetailsService implements UserDetailsService {

  private final ClientService clientService;

  /**
   * Аутентификация по email+password
   */
  @Override
  public UserJwt loadUserByUsername(String username) throws UsernameNotFoundException {
    ClientDto client = clientService.getClientByMail(username);
    if (client == null) {
      return null;
    }

    return new UserJwt(client.getId(), username, client.getPassword());
  }
}