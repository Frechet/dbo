package com.task.dbo.service;

import static com.task.dbo.utils.ServiceConstants.AMOUNT_IS_INFINITE;
import static com.task.dbo.utils.ServiceConstants.AMOUNT_IS_NAN;
import static com.task.dbo.utils.ServiceConstants.NOT_ENOUGH_FUNDS;
import static com.task.dbo.utils.ServiceConstants.RECIPIENT_ID_ACCOUNT_NOT_FOUND;
import static com.task.dbo.utils.ServiceConstants.SENDER_ID_ACCOUNT_NOT_FOUND;

import com.task.dbo.domain.Account;
import com.task.dbo.dto.ClientDto;
import com.task.dbo.exception.BadRequestException;
import com.task.dbo.exception.ExceptionMessageCreator;
import com.task.dbo.exception.NotFoundException;
import com.task.dbo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final ExceptionMessageCreator messageCreator;

  private final BigDecimal balanceRaiseLimit = new BigDecimal(2.07);
  private final BigDecimal coefficient = new BigDecimal(1.1);

  @Transactional
  public void incrementBalance(ClientDto client) {
      Account account = accountRepository.getFirstByClientId(client.getId())
          .orElseThrow(() -> NotFoundException.of(messageCreator.createMessage(RECIPIENT_ID_ACCOUNT_NOT_FOUND)));
      BigDecimal newBalance = account.getBalance().multiply(coefficient);
      if (!(newBalance.compareTo(account.getBalanceInitial().multiply(balanceRaiseLimit)) > 0)) {
        account.setBalance(newBalance);
        log.info("client ID - {}, current  balance - {}", account.getClient().getId(),
            account.getBalance().setScale(2, RoundingMode.HALF_UP));
      } else {
        log.info("client ID - {}  reached the limit, init balance - {}, current  balance - {}", account.getClient().getId(),
            account.getBalanceInitial().setScale(2, RoundingMode.HALF_UP),
            account.getBalance().setScale(2, RoundingMode.HALF_UP));
      }
  }

  @Transactional
  public boolean transferMoney(long transferFrom, long transferTo, double value) {

    if (Double.isInfinite(value)) {
      throw NotFoundException.of(messageCreator.createMessage(AMOUNT_IS_INFINITE));
    }

    if (Double.isNaN(value)) {
      throw NotFoundException.of(messageCreator.createMessage(AMOUNT_IS_NAN));
    }

    BigDecimal valueBigDecimal = new BigDecimal(value);

    Account senderAccount = accountRepository.getFirstByClientId(transferFrom)
        .orElseThrow(() -> NotFoundException.of(messageCreator.createMessage(SENDER_ID_ACCOUNT_NOT_FOUND)));
    Account recipientAccount = accountRepository.getFirstByClientId(transferTo)
        .orElseThrow(() -> NotFoundException.of(messageCreator.createMessage(RECIPIENT_ID_ACCOUNT_NOT_FOUND)));

    if (senderAccount.getBalance().compareTo(valueBigDecimal) < 0) {
      throw BadRequestException.of(messageCreator.createMessage(NOT_ENOUGH_FUNDS));
    }

    senderAccount.setBalance(senderAccount.getBalance().subtract(valueBigDecimal));
    log.info("senderAccount ID - {}, send - {}, current  balance - {}", senderAccount.getClient().getId(),
        valueBigDecimal.setScale(2, RoundingMode.HALF_UP),
        senderAccount.getBalance().setScale(2, RoundingMode.HALF_UP));

    recipientAccount.setBalance(recipientAccount.getBalance().add(valueBigDecimal));
    log.info("recipientAccount ID - {}, receive - {}, current  balance - {}",
        recipientAccount.getClient().getId(),
        valueBigDecimal.setScale(2, RoundingMode.HALF_UP),
        recipientAccount.getBalance().setScale(2, RoundingMode.HALF_UP));
    return true;
  }

}