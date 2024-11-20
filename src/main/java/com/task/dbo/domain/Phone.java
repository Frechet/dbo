package com.task.dbo.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Entity
@Table(name = "phone_data")
@Setter
@Getter
@NoArgsConstructor
public class Phone {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "client_id")
  private Client client;

  @Pattern(regexp = "7\\d{10}",
      message = "номер телефона не соответствует ожидаемому формату")
  private String phone;
}