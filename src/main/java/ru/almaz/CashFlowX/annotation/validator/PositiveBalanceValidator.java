package ru.almaz.CashFlowX.annotation.validator;

import ru.almaz.CashFlowX.annotation.PositiveBalance;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class PositiveBalanceValidator implements ConstraintValidator<PositiveBalance, BigDecimal> {

    @Override
    public void initialize(PositiveBalance constraintAnnotation) {
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.compareTo(BigDecimal.ZERO) >= 0;
    }
}
