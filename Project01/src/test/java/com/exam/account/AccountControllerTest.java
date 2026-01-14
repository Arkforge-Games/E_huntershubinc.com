package com.exam.account;

import com.exam.account.enums.AccountType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createAccount_Success_ShouldReturn201() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("customerName", "Test");
        request.put("customerMobile", "09081234567");
        request.put("customerEmail", "test12345@gmail.com");
        request.put("address1", "test");
        request.put("address2", "test");
        request.put("accountType", "S");

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerNumber").exists())
                .andExpect(jsonPath("$.transactionStatusCode").value(201))
                .andExpect(jsonPath("$.transactionStatusDescription").value("Customer account created"));
    }

    @Test
    public void createAccount_MissingEmail_ShouldReturn400() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("customerName", "Test");
        request.put("customerMobile", "09081234567");
        request.put("address1", "test");
        request.put("accountType", "S");

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(400))
                .andExpect(jsonPath("$.transactionStatusDescription").value("Email is required field"));
    }

    @Test
    public void createAccount_InvalidEmail_ShouldReturn400() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("customerName", "Test");
        request.put("customerMobile", "09081234567");
        request.put("customerEmail", "invalid-email");
        request.put("address1", "test");
        request.put("accountType", "S");

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(400))
                .andExpect(jsonPath("$.transactionStatusDescription").value("Invalid email format"));
    }

    @Test
    public void createAccount_MissingAccountType_ShouldReturn400() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("customerName", "Test");
        request.put("customerMobile", "09081234567");
        request.put("customerEmail", "test@gmail.com");
        request.put("address1", "test");

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(400))
                .andExpect(jsonPath("$.transactionStatusDescription").value("Account type is required"));
    }

    @Test
    public void createAccount_InvalidAccountType_ShouldReturn400() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("customerName", "Test");
        request.put("customerMobile", "09081234567");
        request.put("customerEmail", "test@gmail.com");
        request.put("address1", "test");
        request.put("accountType", "X");

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(400));
    }

    @Test
    public void createAccount_CheckingAccount_ShouldReturn201() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("customerName", "John Doe");
        request.put("customerMobile", "09171234567");
        request.put("customerEmail", "john.doe@example.com");
        request.put("address1", "123 Main St");
        request.put("accountType", "C");

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerNumber").exists())
                .andExpect(jsonPath("$.transactionStatusCode").value(201))
                .andExpect(jsonPath("$.transactionStatusDescription").value("Customer account created"));
    }

    @Test
    public void createAccount_MissingCustomerName_ShouldReturn400() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("customerMobile", "09081234567");
        request.put("customerEmail", "test@gmail.com");
        request.put("address1", "test");
        request.put("accountType", "S");

        mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(400));
    }

    // ==================== Customer Inquiry Tests ====================

    @Test
    public void getCustomer_Success_ShouldReturn302() throws Exception {
        // First create a customer
        Map<String, Object> request = new HashMap<>();
        request.put("customerName", "Inquiry Test");
        request.put("customerMobile", "09081234567");
        request.put("customerEmail", "inquiry.test@gmail.com");
        request.put("address1", "123 Test St");
        request.put("address2", "Unit 1");
        request.put("accountType", "S");

        String createResponse = mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // Extract customer number from response
        Map<String, Object> responseMap = objectMapper.readValue(createResponse, Map.class);
        String customerNumber = (String) responseMap.get("customerNumber");

        // Now query the customer
        mockMvc.perform(get("/api/v1/account/" + customerNumber))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.customerNumber").value(customerNumber))
                .andExpect(jsonPath("$.customerName").value("Inquiry Test"))
                .andExpect(jsonPath("$.customerMobile").value("09081234567"))
                .andExpect(jsonPath("$.customerEmail").value("inquiry.test@gmail.com"))
                .andExpect(jsonPath("$.address1").value("123 Test St"))
                .andExpect(jsonPath("$.address2").value("Unit 1"))
                .andExpect(jsonPath("$.savings").isArray())
                .andExpect(jsonPath("$.savings[0].accountType").value("Savings"))
                .andExpect(jsonPath("$.transactionStatusCode").value(302))
                .andExpect(jsonPath("$.transactionStatusDescription").value("Customer Account found"));
    }

    @Test
    public void getCustomer_NotFound_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/v1/account/99999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.transactionStatusCode").value(401))
                .andExpect(jsonPath("$.transactionStatusDescription").value("Customer not found"));
    }

    @Test
    public void getCustomer_WithCheckingAccount_ShouldReturn302() throws Exception {
        // Create a customer with checking account
        Map<String, Object> request = new HashMap<>();
        request.put("customerName", "Checking Test");
        request.put("customerMobile", "09171234567");
        request.put("customerEmail", "checking.test@gmail.com");
        request.put("address1", "456 Check Ave");
        request.put("accountType", "C");

        String createResponse = mockMvc.perform(post("/api/v1/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Map<String, Object> responseMap = objectMapper.readValue(createResponse, Map.class);
        String customerNumber = (String) responseMap.get("customerNumber");

        // Query the customer
        mockMvc.perform(get("/api/v1/account/" + customerNumber))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.customerNumber").value(customerNumber))
                .andExpect(jsonPath("$.checking").isArray())
                .andExpect(jsonPath("$.checking[0].accountType").value("Checking"))
                .andExpect(jsonPath("$.transactionStatusCode").value(302));
    }
}
