package ru.almaz.CashFlowX.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.almaz.CashFlowX.entity.BankAccount;
import ru.almaz.CashFlowX.repository.BankAccountRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;


    @Scheduled(fixedRate = 600000)
    public void applyInterest() {
        log.info("Начало работы метода увеличения начального депозита");
        List<BankAccount> accounts = bankAccountRepository.findAll();
        for (BankAccount account : accounts) {
            BigDecimal balance = account.getBalance();
            BigDecimal newBalance = balance.multiply(BigDecimal.valueOf(1.05));

            BigDecimal maxDeposit = account.getDeposit().multiply(BigDecimal.valueOf(2.07));
            if (newBalance.compareTo(maxDeposit) > 0) {
                return;
            }
            account.setBalance(newBalance);
        }
        bankAccountRepository.saveAll(accounts);
        log.info("Конец работы метода увеличения начального депозита");
    }
}
