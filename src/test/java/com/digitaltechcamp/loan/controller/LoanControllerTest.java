package com.digitaltechcamp.loan.controller;

import com.digitaltechcamp.loan.constants.LoanError;
import com.digitaltechcamp.loan.exception.LoadException;
import com.digitaltechcamp.loan.model.LoanInfoModel;
import com.digitaltechcamp.loan.service.LoanService;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.MockitoAnnotations;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanControllerTest {
    @Mock
    LoanService loanService;

    @InjectMocks
    LoanController loanController;

    private MockMvc mvc;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        loanController = new LoanController(loanService);
        mvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }

    @DisplayName("Test Get loan info by id equals 1 should return loan information")
    @Test
    void getLoanInfoCByIdEquals1() throws Exception {
        Long reqId = 1L;
        LoanInfoModel loanInfoModel = new LoanInfoModel();
        loanInfoModel.setId(1L);
        loanInfoModel.setStatus("Ok");
        loanInfoModel.setAccountPayable("x102-222-2200");
        loanInfoModel.setAccountReceivable("x102-222-2200");
        loanInfoModel.setPrincipleAmount(3000000.00);
        when(loanService.getLoanInfoById(reqId)).thenReturn(loanInfoModel);

        MvcResult mvcResult = mvc.perform(get("/loan/info/"+reqId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resp = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status = new JSONObject(resp.getString("status"));
        JSONObject data = new JSONObject(resp.getString("data"));

        assertEquals("0",status.get("code").toString());
        assertEquals("success",status.get("message").toString());
        assertEquals(1,data.get("id"));
        assertEquals("Ok",data.get("status"));
        assertEquals("x102-222-2200",data.get("account_payable"));
        assertEquals("x102-222-2200",data.get("account_receivable"));
        assertEquals(3000000.00,data.getDouble("principle_amount")); //get set defalut int and string if other you should identifine type

        verify(loanService, times(1)).getLoanInfoById(reqId);
    }
    @DisplayName("Test get loan info by id equals 2 should throw loan")
    @Test
    void testGetLoanInfoByIdEquals2() throws Exception{
        Long reqParam = 2L;
        when(loanService.getLoanInfoById(reqParam)).thenThrow(
                new LoadException(LoanError.GET_LOAN_INFO_NOT_FOUND, HttpStatus.BAD_REQUEST)
        );
        MvcResult mvcResult = mvc.perform(get("/loan/info/"+reqParam))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resp = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status = new JSONObject(resp.getString("status"));

        assertEquals("LOAN4002", status.get("code").toString());
        assertEquals("Loan information not found",status.get("message").toString());
    }
    @DisplayName("Test get loan info by id equals 3 should throw Exception")
    @Test
    void testGetLoanInfoByIdEquals3() throws Exception{
        Long reqParam = 3L;
        when(loanService.getLoanInfoById(reqParam)).thenThrow(
                new Exception("Test throw new exception")
        );
        MvcResult mvcResult = mvc.perform(get("/loan/info/"+reqParam))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();
        JSONObject resp = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status = new JSONObject(resp.getString("status"));

        assertEquals("LOAN4001", status.get("code").toString());
        assertEquals("Cannot get loan information",status.get("message").toString());

    }

}
