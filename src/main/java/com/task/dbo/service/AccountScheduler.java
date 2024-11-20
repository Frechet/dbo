package com.task.dbo.service;

import com.task.dbo.dto.ClientDto;
import com.task.dbo.exception.ExceptionMessageCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountScheduler {

  private final AccountService accountService;
  private final ClientService clientService;
  private final ExceptionMessageCreator messageCreator;


  // TODO: добавить ShedLock для возможности горизонтального масштабировании
  @Scheduled(fixedRateString = "${app.deposit.scheduler.interval}", initialDelayString = "${app.deposit.scheduler.interval}")
  public void incrementBalanceSchedule() {

    // Берём всех клиентов, предполагая что клиент = аккаунт,
    // но запросив клиентов изначально мы не получим блокировку на работу переводов
    // и уже по клиентам сможем провести итеративное обновление аккаунтов
    List<ClientDto> clients = clientService.getAllClients(); // TODO: заменить findAll на постраничное асинхронное прохождение

    clients.forEach(client -> {
      accountService.incrementBalance(client);
    });
  }
}