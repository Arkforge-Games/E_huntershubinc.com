package com.exam.account.dto;

public class AccountResponse {

    private String customerNumber;
    private Integer transactionStatusCode;
    private String transactionStatusDescription;

    public AccountResponse() {
    }

    public AccountResponse(String customerNumber, Integer transactionStatusCode, String transactionStatusDescription) {
        this.customerNumber = customerNumber;
        this.transactionStatusCode = transactionStatusCode;
        this.transactionStatusDescription = transactionStatusDescription;
    }

    public static AccountResponse success(String customerNumber) {
        return new AccountResponse(customerNumber, 201, "Customer account created");
    }

    public static AccountResponse error(Integer statusCode, String message) {
        return new AccountResponse(null, statusCode, message);
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public Integer getTransactionStatusCode() {
        return transactionStatusCode;
    }

    public void setTransactionStatusCode(Integer transactionStatusCode) {
        this.transactionStatusCode = transactionStatusCode;
    }

    public String getTransactionStatusDescription() {
        return transactionStatusDescription;
    }

    public void setTransactionStatusDescription(String transactionStatusDescription) {
        this.transactionStatusDescription = transactionStatusDescription;
    }
}
