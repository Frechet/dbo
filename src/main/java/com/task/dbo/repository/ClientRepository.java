package com.task.dbo.repository;

import com.task.dbo.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface ClientRepository extends JpaRepository<Client, Long> {

  Page<Client> findByNameContainingIgnoreCaseOrderByName(Pageable pageable, String name);

  Page<Client> findByBirthdateAfterOrderByBirthdate(Pageable pageable, LocalDate birthdate);
}