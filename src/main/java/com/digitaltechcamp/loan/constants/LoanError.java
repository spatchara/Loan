package com.digitaltechcamp.loan.constants;

import lombok.Getter;

@Getter
public enum LoanError {
    GET_LOAN_INFO_EXCEPTION("LOAN4001", "Cannot get loan information"),
    GET_LOAN_INFO_NOT_FOUND("LOAN4002", "Loan information not found");
//    GET_LOAN_INFO_NOT_FOUND("LOAN4002", "Loan information not found");

    private String code;
    private String message;
    //create Constructor to receive
    LoanError(String code, String message){
        this.code = code;
        this.message = message;
    }
}
