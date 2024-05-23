package ru.almaz.CashFlowX.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransferDTO {

    @NotBlank(message = "Имя пользователя получателя является обязательным")
    private String recipientUsername;

    @NotNull(message = "Сумма является обязательной")
    @DecimalMin(value = "0.01", message = "Сумма должна быть больше 0")
    private BigDecimal amount;
}
