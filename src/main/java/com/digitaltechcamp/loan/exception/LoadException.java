package com.digitaltechcamp.loan.exception;

import com.digitaltechcamp.loan.constants.LoanError;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class LoadException  extends Exception{
    private LoanError loanError;
    private HttpStatus httpStatus = HttpStatus.OK;

//    public LoadException(HttpStatus httpStatus){
////        this.loanError = loanError;
//        this.httpStatus= httpStatus;
//    }
    public LoadException(LoanError loanError, HttpStatus httpStatus){
        this.loanError = loanError;
        this.httpStatus= httpStatus;
    }
}
