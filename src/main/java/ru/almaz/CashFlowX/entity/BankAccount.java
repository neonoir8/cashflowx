package ru.almaz.CashFlowX.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.almaz.CashFlowX.annotation.PositiveBalance;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PositiveBalance(message = "Баланс не может быть отрицательным")
    @Column(nullable = false, precision = 38, scale = 2)
    private BigDecimal balance;

    @Column
    private BigDecimal deposit;

    @OneToOne(mappedBy = "bankAccount")
    private User user;
}
