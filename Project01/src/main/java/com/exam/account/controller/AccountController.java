package com.exam.account.controller;

import com.exam.account.dto.AccountRequest;
import com.exam.account.dto.AccountResponse;
import com.exam.account.dto.CustomerInquiryResponse;
import com.exam.account.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest request) {
        AccountResponse response = accountService.createAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{customerNumber}")
    public ResponseEntity<CustomerInquiryResponse> getCustomer(@PathVariable String customerNumber) {
        CustomerInquiryResponse response = accountService.getCustomerByNumber(customerNumber);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @GetMapping
    public ResponseEntity<AccountResponse> getAccountWithoutNumber() {
        AccountResponse response = AccountResponse.error(400, "Customer number is required. Use GET /api/v1/account/{customerNumber}");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
