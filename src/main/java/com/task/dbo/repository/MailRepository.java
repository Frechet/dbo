package com.task.dbo.repository;

import com.task.dbo.domain.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface MailRepository extends JpaRepository<Mail, Long> {

  Optional<Mail> getFirstByMail(String mail);
}