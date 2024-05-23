package ru.almaz.CashFlowX.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.almaz.CashFlowX.dto.TransferDTO;
import ru.almaz.CashFlowX.entity.BankAccount;
import ru.almaz.CashFlowX.entity.User;
import ru.almaz.CashFlowX.repository.BankAccountRepository;
import ru.almaz.CashFlowX.repository.UserRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferService {
    private static final Logger log = LoggerFactory.getLogger(TransferService.class);
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;

    @Transactional
    public void transfer(String senderUsername, TransferDTO request) {
        User sender = userRepository.findByUsername(senderUsername)
                .orElseThrow(() -> new RuntimeException("Отправитель не найден"));

        User recipient = userRepository.findByUsername(request.getRecipientUsername())
                .orElseThrow(() -> new RuntimeException("Получатель не найден"));

        BankAccount senderAccount = sender.getBankAccount();
        BankAccount recipientAccount = recipient.getBankAccount();
        validateBalance(request, senderAccount);
        validateTransferingSumNotDecrease(request);
        senderAccount.setBalance(senderAccount.getBalance().subtract(request.getAmount()));
        BigDecimal sum = recipientAccount.getBalance().add(request.getAmount());
        recipientAccount.setBalance(sum);

        bankAccountRepository.save(senderAccount);
        bankAccountRepository.save(recipientAccount);
    }

    private static void validateBalance(TransferDTO request, BankAccount senderAccount) {
        if (senderAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Недостаточно средств");
        }
    }

    private void validateTransferingSumNotDecrease(TransferDTO request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Переводимая сумма должна быть больше 0");
        }
    }
}
