package ru.almaz.CashFlowX.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
public class UserDTO {

    private String username;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    private String fullName;
    private String birthDate;
    @NotBlank(message = "Адрес электронной почты обязателен")
    @Email(message = "Адрес электронной почты должен быть действительным")
    private String email;
    @NotBlank(message = "Номер телефона обязателен")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Некорректный номер телефона")
    private String phone;
    private BigDecimal balance;
    private BankAccountDTO bankAccount;
}
