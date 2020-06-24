package com.digitaltechcamp.loan.controller;

import com.digitaltechcamp.loan.constants.LoanError;
import com.digitaltechcamp.loan.constants.Response;
import com.digitaltechcamp.loan.exception.LoadException;
import com.digitaltechcamp.loan.model.LoanInfoModel;
import com.digitaltechcamp.loan.model.ResponseModel;
import com.digitaltechcamp.loan.model.StatusModel;
import com.digitaltechcamp.loan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import java.util.logging.LogManager;
//import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/loan")
public class LoanController {
    private static final Logger log = LogManager.getLogger(LoanController.class.getName());

    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService){
        this.loanService =  loanService;

    }
    @GetMapping("/info/{id}")
    public HttpEntity<ResponseModel> getLoanInfoById(@PathVariable Long id) throws Exception {

        try {
            log.info("Get Loan info by customer id: {}", id);
            LoanInfoModel loanInfoResponse = loanService.getLoanInfoById(id);
            log.info("Response id: {}, status is : {}", loanInfoResponse.getId(),
                    loanInfoResponse.getStatus());
            StatusModel statusModel = new StatusModel(Response.SUCCESS_CODE.getContent(),Response.SUCCESS.getContent());
            return ResponseEntity.ok(new ResponseModel(statusModel, loanInfoResponse));
        } catch (LoadException e){
            log.error("Loan Exception by id: {}", id);
            LoanError loanError = e.getLoanError();
            StatusModel statusModel = new StatusModel(Response.FAIL_CODE.getContent(),loanError.getMessage());
            return ResponseEntity.ok(new ResponseModel(statusModel,loanError.getCode()));

        } catch ( Exception e){
            log.error("Exception by id: {}", id);
            LoanError loanError = LoanError.GET_LOAN_INFO_EXCEPTION;
            StatusModel statusModel = new StatusModel(Response.FAIL_CODE.getContent(),loanError.getMessage());
            return new ResponseModel(
                    new StatusModel(
                            Response.FAIL_CODE.getContent(),
                            loanError.getMessage()),loanError.getCode()
                    ).build(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
