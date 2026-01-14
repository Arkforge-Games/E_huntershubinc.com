package com.exam.account.dto;

import java.util.List;

public class CustomerInquiryResponse {

    private String customerNumber;
    private String customerName;
    private String customerMobile;
    private String customerEmail;
    private String address1;
    private String address2;
    private List<AccountDto> savings;
    private List<AccountDto> checking;
    private Integer transactionStatusCode;
    private String transactionStatusDescription;

    public CustomerInquiryResponse() {
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public List<AccountDto> getSavings() {
        return savings;
    }

    public void setSavings(List<AccountDto> savings) {
        this.savings = savings;
    }

    public List<AccountDto> getChecking() {
        return checking;
    }

    public void setChecking(List<AccountDto> checking) {
        this.checking = checking;
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
