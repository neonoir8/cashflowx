package ru.almaz.CashFlowX.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.almaz.CashFlowX.entity.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

}
