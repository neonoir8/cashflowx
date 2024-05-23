package ru.almaz.CashFlowX.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountDTO {

    private BigDecimal balance;
    private BigDecimal deposit;
}
