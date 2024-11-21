package com.task.dbo.repository;

import com.task.dbo.domain.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PhoneRepository extends JpaRepository<Phone, Long> {

  Optional<Phone> getFirstByPhone(String phone);

  @Modifying
  @Transactional
  long deleteByClientIdAndPhone(Long clientId, String phone);
}