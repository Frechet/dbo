package com.task.dbo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Client implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private String name;

  @JsonFormat(pattern = "dd.MM.yyyy")
  @DateTimeFormat(pattern = "dd.MM.yyyy")
  @Column(nullable = false, name = "DATE_OF_BIRTH")
  private LocalDate birthdate;

  @Column(nullable = false)
  @Size(min = 8, max = 500, message = "Длина пароля вне диапазона [8 - 500]")
  private String password;

  @Override
  public String toString() {
    return "Client{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", birthdate=" + birthdate +
        '}';
  }
}