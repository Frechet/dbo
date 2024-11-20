package com.task.dbo.controllers;

import com.task.dbo.dto.ClientDto;
import com.task.dbo.service.AccountService;
import com.task.dbo.service.ClientService;
import com.task.dbo.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Validated
@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
@Tag(name = "Клиентские операции")
public class ClientController {

  private final ClientService clientService;
  private final JwtUtil jwtUtil;
  private final AccountService accountService;

  @GetMapping("/getbyphone/{phone}")
  @Operation(summary = "Поиск по телефону")
  @SecurityRequirement(name = "JWT")
  public ResponseEntity<ClientDto> getClientByPhone(@PathVariable String phone) {
    return ResponseEntity.ok().body(clientService.getClientByPhone(phone));
  }

  @GetMapping("/getbyname")
  @Operation(summary = "Поиск по имени")
  @SecurityRequirement(name = "JWT")
  public ResponseEntity<Page<ClientDto>> getClientByName(@RequestParam int page, @RequestParam int size, @RequestParam String name) {
    return ResponseEntity.ok().body(clientService.getClientsByName(page, size, name));
  }

  @GetMapping("/getbymail/{mail}")
  @Operation(summary = "Поиск по почтовому адресу ")
  @SecurityRequirement(name = "JWT")
  public ResponseEntity<ClientDto> getClientByMail(@PathVariable String mail) {
    return ResponseEntity.ok().body(clientService.getClientByMail(mail));
  }

  @GetMapping("/getbybirthdate")
  @Operation(summary = "Поиск по дате рождения")
  @SecurityRequirement(name = "JWT")
  public ResponseEntity<Page<ClientDto>> getClientByBirthdate(@RequestParam int page, @RequestParam int size,
      @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate birthdate) {
    return ResponseEntity.ok().body(clientService.getClientsByBirthdate(page, size, birthdate));
  }

  /**
   * Трансфер денег от одного пользователя к другому. Вход: USER_ID (transfer from) – берем у авторизованного из Claim токена, USER_ID
   * (transfer to) из запроса, VALUE (сумма перевода) из запроса. То есть у того, кто переводит мы списываем эту сумму, у того, кому
   * переводим – добавляем эту сумму. Считать эту операцию «банковской» (высоко-значимой), сделать ее со всеми нужными валидациями (надо
   * подумать какими) и потоко-защищенной.
   */
  @PutMapping("/transfermoney")
  @Operation(summary = "Трансфер средств между клиентами")
  @SecurityRequirement(name = "JWT")
  public ResponseEntity<Boolean> transferMoney(@RequestParam long transferTo, @RequestParam double value) {
    return ResponseEntity.ok(accountService.transferMoney(jwtUtil.extractUserId(), transferTo, value));
  }
}