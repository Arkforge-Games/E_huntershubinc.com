package com.exam.account.service;

import com.exam.account.dto.AccountDto;
import com.exam.account.dto.AccountRequest;
import com.exam.account.dto.AccountResponse;
import com.exam.account.dto.CustomerInquiryResponse;
import com.exam.account.enums.AccountType;
import com.exam.account.exception.CustomerNotFoundException;
import com.exam.account.model.Account;
import com.exam.account.model.Customer;
import com.exam.account.repository.AccountRepository;
import com.exam.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final Random random = new Random();

    public AccountService(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public AccountResponse createAccount(AccountRequest request) {
        // Generate unique customer number (8 digits)
        String customerNumber = generateUniqueCustomerNumber();

        // Create Customer entity
        Customer customer = new Customer();
        customer.setCustomerNumber(customerNumber);
        customer.setCustomerName(request.getCustomerName());
        customer.setCustomerMobile(request.getCustomerMobile());
        customer.setCustomerEmail(request.getCustomerEmail());
        customer.setAddress1(request.getAddress1());
        customer.setAddress2(request.getAddress2());

        // Create Account entity
        Account account = new Account();
        account.setAccountNumber(generateUniqueAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setAvailableBalance(BigDecimal.ZERO);

        // Associate account with customer
        customer.addAccount(account);

        // Save customer (cascades to account)
        customerRepository.save(customer);

        return AccountResponse.success(customerNumber);
    }

    private String generateUniqueCustomerNumber() {
        String customerNumber;
        do {
            customerNumber = String.format("%08d", random.nextInt(100000000));
        } while (customerRepository.findByCustomerNumber(customerNumber).isPresent());
        return customerNumber;
    }

    private Long generateUniqueAccountNumber() {
        Long accountNumber;
        do {
            accountNumber = 10000L + random.nextInt(90000);
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    @Transactional(readOnly = true)
    public CustomerInquiryResponse getCustomerByNumber(String customerNumber) {
        Customer customer = customerRepository.findByCustomerNumber(customerNumber)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        CustomerInquiryResponse response = new CustomerInquiryResponse();
        response.setCustomerNumber(customer.getCustomerNumber());
        response.setCustomerName(customer.getCustomerName());
        response.setCustomerMobile(customer.getCustomerMobile());
        response.setCustomerEmail(customer.getCustomerEmail());
        response.setAddress1(customer.getAddress1());
        response.setAddress2(customer.getAddress2());

        // Separate accounts by type
        List<AccountDto> savingsAccounts = customer.getAccounts().stream()
                .filter(acc -> acc.getAccountType() == AccountType.S)
                .map(this::mapToAccountDto)
                .collect(Collectors.toList());

        List<AccountDto> checkingAccounts = customer.getAccounts().stream()
                .filter(acc -> acc.getAccountType() == AccountType.C)
                .map(this::mapToAccountDto)
                .collect(Collectors.toList());

        response.setSavings(savingsAccounts.isEmpty() ? null : savingsAccounts);
        response.setChecking(checkingAccounts.isEmpty() ? null : checkingAccounts);
        response.setTransactionStatusCode(302);
        response.setTransactionStatusDescription("Customer Account found");

        return response;
    }

    private AccountDto mapToAccountDto(Account account) {
        return new AccountDto(
                account.getAccountNumber(),
                account.getAccountType().getDescription(),
                account.getAvailableBalance()
        );
    }
}
