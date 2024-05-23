package ru.almaz.CashFlowX.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.almaz.CashFlowX.dto.TransferDTO;
import ru.almaz.CashFlowX.service.TransferService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
@Validated
public class MoneyTransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<?> moneyTransfer(@Valid @RequestBody TransferDTO request) {
        String senderUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        transferService.transfer(senderUsername, request);
        return ResponseEntity.ok().build();
    }
}
