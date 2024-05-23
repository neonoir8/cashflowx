package ru.almaz.CashFlowX.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.almaz.CashFlowX.entity.User;

import java.time.LocalDate;

public class UserSpecification {

    public static Specification<User> hasBirthDateAfter(LocalDate birthDate) {
        return (root, query, cb) -> cb.greaterThan(root.get("birthDate"), birthDate);
    }

    public static Specification<User> hasPhone(String phone) {
        return (root, query, cb) -> cb.equal(root.get("phone"), phone);
    }

    public static Specification<User> hasFullNameLike(String fullName) {
        return (root, query, cb) -> cb.like(root.get("fullName"), fullName + "%");
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, cb) -> cb.equal(root.get("email"), email);
    }
}
