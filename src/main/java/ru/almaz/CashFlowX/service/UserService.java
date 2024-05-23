package ru.almaz.CashFlowX.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.almaz.CashFlowX.converter.UserConverter;
import ru.almaz.CashFlowX.dto.UpdateContactDTO;
import ru.almaz.CashFlowX.dto.UserDTO;
import ru.almaz.CashFlowX.entity.User;
import ru.almaz.CashFlowX.exception.EmailAlreadyExistsException;
import ru.almaz.CashFlowX.exception.PhoneAlreadyExistsException;
import ru.almaz.CashFlowX.exception.UsernameAlreadyExistsException;
import ru.almaz.CashFlowX.repository.UserRepository;
import ru.almaz.CashFlowX.specifications.UserSpecification;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserConverter userConverter;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Такой юзернейм уже существует");
        }
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Такая почта уже существует");
        }
        if (userRepository.findByPhone(userDTO.getPhone()).isPresent()) {
            throw new PhoneAlreadyExistsException("Такой номер телефона уже существует");
        }

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        User user = userConverter.toUser(userDTO, encodedPassword);
        user = userRepository.save(user);
        return userConverter.toUserDTO(user);
    }


    @Transactional
    public void updateContacts(UpdateContactDTO request) {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        if (StringUtils.hasText(request.getEmail()) && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new RuntimeException("Почта уже занята");
            }
            user.setEmail(request.getEmail());
        }
        if (StringUtils.hasText(request.getPhone()) && !request.getPhone().equals(user.getPhone())) {
            if (userRepository.findByPhone(request.getPhone()).isPresent()) {
                throw new RuntimeException("Номер телефона уже занят");
            }
            user.setPhone(request.getPhone());
        }

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public Page<User> search(Optional<LocalDate> birthDate, Optional<String> phone, Optional<String> fullName, Optional<String> email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<User> spec = Specification.where(null);

        if (birthDate.isPresent()) {
            spec = spec.and(UserSpecification.hasBirthDateAfter(birthDate.get()));
        }
        if (phone.isPresent()) {
            spec = spec.and(UserSpecification.hasPhone(phone.get()));
        }
        if (fullName.isPresent()) {
            spec = spec.and(UserSpecification.hasFullNameLike(fullName.get()));
        }
        if (email.isPresent()) {
            spec = spec.and(UserSpecification.hasEmail(email.get()));
        }
        return userRepository.findAll(spec, pageable);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        userRepository.delete(user);
    }
}
