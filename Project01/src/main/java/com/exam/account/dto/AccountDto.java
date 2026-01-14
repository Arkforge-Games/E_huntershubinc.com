package com.exam.account.dto;

import java.math.BigDecimal;

public class AccountDto {

    private Long accountNumber;
    private String accountType;
    private BigDecimal availableBalance;

    public AccountDto() {
    }

    public AccountDto(Long accountNumber, String accountType, BigDecimal availableBalance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.availableBalance = availableBalance;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }
}
