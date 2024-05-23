package ru.almaz.CashFlowX.exception;

public class PhoneAlreadyExistsException extends RuntimeException{
    public PhoneAlreadyExistsException(String message) {
        super(message);
    }
}
