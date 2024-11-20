package com.task.dbo.service;

import static com.task.dbo.utils.ServiceConstants.MAIL_NOT_FOUND;
import static com.task.dbo.utils.ServiceConstants.PHONE_NOT_FOUND;
import com.task.dbo.domain.Mail;
import com.task.dbo.domain.Phone;
import com.task.dbo.dto.ClientDto;
import com.task.dbo.exception.NotFoundException;
import com.task.dbo.exception.ExceptionMessageCreator;
import com.task.dbo.mapper.ClientMapper;
import com.task.dbo.repository.MailRepository;
import com.task.dbo.repository.PhoneRepository;
import com.task.dbo.repository.ClientRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

  private final ClientRepository clientRepository;
  private final PhoneRepository phoneRepository;
  private final MailRepository mailRepository;
  private final ExceptionMessageCreator messageCreator;
  private final ClientMapper clientMapper;

  /**
   * TODO: заменить на постраничное получение
   */
  public List<ClientDto> getAllClients() {
    return clientMapper.toDto(clientRepository.findAll());
  }

  public Page<ClientDto> getClientsByName(int page, int size, String name) {
    Pageable pageable = PageRequest.of(page, size);
    return clientRepository.findByNameContainingIgnoreCaseOrderByName(pageable, name).map(clientMapper::toDto);
  }

  public Page<ClientDto> getClientsByBirthdate(int page, int size, LocalDate birthdate) {
    Pageable pageable = PageRequest.of(page, size);
    return clientRepository.findByBirthdateAfterOrderByBirthdate(pageable, birthdate).map(clientMapper::toDto);
  }

  @Cacheable(value = "getClientByPhone", key="#phone")
  public ClientDto getClientByPhone(String phone) {
    Phone p = phoneRepository.getFirstByPhone(phone).orElseThrow(() -> NotFoundException.of(messageCreator.createMessage(PHONE_NOT_FOUND)));
    return clientMapper.toDto(p.getClient());
  }

  public ClientDto getClientByMail(String mail) {
    Mail m = mailRepository.getFirstByMail(mail).orElseThrow(() -> NotFoundException.of(messageCreator.createMessage(MAIL_NOT_FOUND)));
    return clientMapper.toDto(m.getClient());
  }

}