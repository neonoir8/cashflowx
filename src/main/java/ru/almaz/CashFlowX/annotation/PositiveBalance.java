package ru.almaz.CashFlowX.annotation;

import ru.almaz.CashFlowX.annotation.validator.PositiveBalanceValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PositiveBalanceValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveBalance {
    String message() default "Balance must be positive";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
