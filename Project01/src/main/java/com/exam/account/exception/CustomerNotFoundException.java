package com.exam.account.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(String customerNumber, boolean isCustomerNumber) {
        super("Customer not found");
    }
}
