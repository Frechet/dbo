package com.task.dbo.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Account {

  @Id
  @SequenceGenerator(name = "account_id_seq", sequenceName = "account_id_seq", allocationSize = 1)
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="account_id_seq")
  private Long id;

  @Min(value = 0, message = "Баланс не может опуститься ниже 0")
  private BigDecimal balance = new BigDecimal(0);

  @Min(value = 0, message = "Баланс не может опуститься ниже 0")
  private BigDecimal balanceInitial = new BigDecimal(0);

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id")
  private Client client;

}