package com.task.dbo.service;

import static com.task.dbo.utils.ServiceConstants.CLIENT_ID_NOT_FOUND;
import static com.task.dbo.utils.ServiceConstants.MAIL_NOT_FOUND;
import static com.task.dbo.utils.ServiceConstants.PHONE_NOT_AVAILABLE;
import static com.task.dbo.utils.ServiceConstants.PHONE_NOT_FOUND;

import com.task.dbo.domain.Client;
import com.task.dbo.domain.Mail;
import com.task.dbo.domain.Phone;
import com.task.dbo.dto.ClientDto;
import com.task.dbo.dto.PhoneDto;
import com.task.dbo.exception.NotFoundException;
import com.task.dbo.exception.ExceptionMessageCreator;
import com.task.dbo.mapper.ClientMapper;
import com.task.dbo.mapper.PhoneMapper;
import com.task.dbo.repository.MailRepository;
import com.task.dbo.repository.PhoneRepository;
import com.task.dbo.repository.ClientRepository;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

  private final ClientRepository clientRepository;
  private final PhoneRepository phoneRepository;
  private final MailRepository mailRepository;
  private final ExceptionMessageCreator messageCreator;
  private final ClientMapper clientMapper;
  private final PhoneMapper phoneMapper;

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

  // TODO: может быть не удачная попытка применить телефон чужого пользователя,
  //   при этом мы сбросим кеш. Поэтому следует реализовать предварительный слой валидаций до работы с кешом.
  @CacheEvict(value = "getClientByPhone", key="#phone")
  @Transactional
  public PhoneDto putClientPhone(Long clientId, @NotBlank @Size(max = 12) String phone) {
    if (clientId == null) {
      throw NotFoundException.of(messageCreator.createMessage(CLIENT_ID_NOT_FOUND));
    }
    return phoneMapper.toDto(phoneRepository.getFirstByPhone(phone)
        .map(p -> {
          if (!Objects.equals(p.getClient().getId(), clientId)) {
            throw NotFoundException.of(messageCreator.createMessage(PHONE_NOT_AVAILABLE));
          }
          return p;
        })
        .orElseGet(() -> {
          log.info("Create new phone - {} for client - {}", phone, clientId);
          Client c = clientRepository.findById(clientId)
              .orElseThrow(() -> NotFoundException.of(messageCreator.createMessage(MAIL_NOT_FOUND)));
          Phone newPhone = new Phone();
          newPhone.setClient(c);
          newPhone.setPhone(phone);
          return phoneRepository.save(newPhone);
        }));
  }

  @CacheEvict(value = "getClientByPhone", key="#phone")
  public boolean deleteClientPhone(Long clientId, @NotBlank @Size(max = 12) String phone) {
    log.info("Delete phone - {} for client - {}", phone, clientId);
    return phoneRepository.deleteByClientIdAndPhone(clientId, phone) > 0;
  }
}