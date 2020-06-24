package com.digitaltechcamp.loan.service;

import com.digitaltechcamp.loan.exception.LoadException;
import com.digitaltechcamp.loan.model.LoanInfoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanServiceTest {
    @InjectMocks
    LoanService loanService;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        loanService = new LoanService();
    }

    @DisplayName("Test Get loan info by id equals 1 should return loan information")
    @Test
    void testGetLoanInfoByIdEquals1() throws Exception {
        LoanInfoModel resp = loanService.getLoanInfoById(1L);

        assertEquals("1", resp.getId().toString());
        assertEquals("Ok", resp.getStatus());
        assertEquals("102-222-2200", resp.getAccountPayable());
        assertEquals("102-222-2200", resp.getAccountReceivable());
        assertEquals(4000000.00, resp.getPrincipleAmount());
    }

    @DisplayName("Test Get loan info by id equals 2 should throw Loan Exception loan info not found r")
    @Test
    void testGetLoanInfoByIdEquals2() throws Exception {
        Long reqParam = 2L;
        LoadException thrown = assertThrows(LoadException.class, () -> loanService.getLoanInfoById(reqParam),
                "Expected loanInfoById(reqParam) to throw, but it didn't");
        assertEquals(400, thrown.getHttpStatus().value());
        assertEquals("LOAN4002",thrown.getLoanError().getCode());
        assertEquals("Loan information not found", thrown.getLoanError().getMessage());
    }

    @DisplayName("Test Get loan info by id equals 3 should throw Loan Exception loan new exception")
    @Test
    void testGetLoanInfoByIdEquals3() throws Exception {
        Long reqParam = 3L;
        Exception thrown = assertThrows(Exception.class,
                () -> loanService.getLoanInfoById(reqParam),
                "Expected loanInfoById(reqparam) to throw, but it didn't");
        assertEquals("Test throw new exception ", thrown.getMessage());
    }
}

