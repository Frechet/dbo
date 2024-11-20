package com.task.dbo.controllers;

import com.task.dbo.dto.AuthenticationRequest;
import com.task.dbo.dto.AuthenticationResponse;
import com.task.dbo.exception.NotFoundException;
import com.task.dbo.exception.ExceptionMessageCreator;
import com.task.dbo.utils.JwtUtil;
import com.task.dbo.service.DboUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.task.dbo.utils.ServiceConstants.INCORRECT_MAIL_OR_PASS;


@RequiredArgsConstructor
@RestController
@Tag(name = "Аутентификация и формирование JWT")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final ExceptionMessageCreator messageCreator;
  private final JwtUtil jwtTokenUtil;
  private final DboUserDetailsService userDetailsService;

  @PostMapping("/authenticate")
  @Operation(summary = "Аутентификация и формирование JWT в теле отклика")
  @SecurityRequirement(name = "JWT")
  public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
      throws NotFoundException {

    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authenticationRequest.getMail(), authenticationRequest.getPassword())
      );
    } catch (BadCredentialsException e) {
      throw NotFoundException.of(messageCreator.createMessage(INCORRECT_MAIL_OR_PASS));
    }

    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getMail());

    final String jwt = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }

}