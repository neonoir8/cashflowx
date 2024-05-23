package ru.almaz.CashFlowX.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.almaz.CashFlowX.converter.UserConverter;
import ru.almaz.CashFlowX.dto.UpdateContactDTO;
import ru.almaz.CashFlowX.dto.UserDTO;
import ru.almaz.CashFlowX.entity.User;
import ru.almaz.CashFlowX.service.UserService;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/user-info")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserService userService;
    private final UserConverter userConverter;

    @PutMapping("/contact")
    public ResponseEntity<?> updateContactInfo(@RequestBody UpdateContactDTO request) {
        userService.updateContacts(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserDTO>> search(@RequestParam(required = false) Optional<LocalDate> birthDate,
                                                @RequestParam(required = false) Optional<String> phone,
                                                @RequestParam(required = false) Optional<String> fullName,
                                                @RequestParam(required = false) Optional<String> email,
                                                @RequestParam(required = false,defaultValue = "0") int page,
                                                @RequestParam(required = false ,defaultValue = "1") int size) {
        Page<User> user = userService.search(birthDate, phone, fullName, email, page, size);
        Page<UserDTO> userDTO = user.map(userConverter::toUserDTO);
        return ResponseEntity.ok(userDTO);
    }
}
