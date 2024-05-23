package ru.almaz.CashFlowX.converter;

import org.springframework.stereotype.Component;
import ru.almaz.CashFlowX.dto.BankAccountDTO;
import ru.almaz.CashFlowX.dto.UserDTO;
import ru.almaz.CashFlowX.entity.BankAccount;
import ru.almaz.CashFlowX.entity.User;

import java.time.LocalDate;

@Component
public class UserConverter {

    public User toUser(UserDTO userDTO, String encodedPassword) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(encodedPassword);
        user.setFullName(userDTO.getFullName());
        user.setBirthDate(LocalDate.parse(userDTO.getBirthDate()));
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setBankAccount(toBankAccount(userDTO.getBankAccount()));

        return user;
    }

    public UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        dto.setFullName(user.getFullName());
        dto.setBirthDate(user.getBirthDate().toString());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setBankAccount(toBankAccountDTO(user.getBankAccount()));
        return dto;
    }

    private BankAccountDTO toBankAccountDTO(BankAccount bankAccount) {
        BankAccountDTO dto = new BankAccountDTO();
        dto.setBalance(bankAccount.getBalance());
        dto.setDeposit(bankAccount.getDeposit());
        return dto;
    }

    private BankAccount toBankAccount(BankAccountDTO bankAccountDTO) {
        BankAccount dto = new BankAccount();
        dto.setBalance(bankAccountDTO.getBalance());
        dto.setDeposit(bankAccountDTO.getDeposit());
        return dto;
    }
}
