package com.digitaltechcamp.loan.service;

import com.digitaltechcamp.loan.constants.LoanError;
import com.digitaltechcamp.loan.exception.LoadException;
import com.digitaltechcamp.loan.model.LoanInfoModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
    private static final Logger log = LogManager.getLogger(LoanService.class.getName());

    public LoanInfoModel getLoanInfoById(Long id) throws Exception {
        log.info("Get loan info by id: {}",id);
        LoanInfoModel loanInfoModel = new LoanInfoModel();
        if(id.equals(1L)) {
            loanInfoModel.setId(1L);
            loanInfoModel.setStatus("Ok");
            loanInfoModel.setAccountPayable("102-222-2200");
            loanInfoModel.setAccountReceivable("102-222-2200");
            loanInfoModel.setPrincipleAmount(4000000.00);
        } else if(id.equals(2L)){
            log.error("id: {}" , id);
            throw new LoadException(LoanError.GET_LOAN_INFO_NOT_FOUND ,HttpStatus.BAD_REQUEST);
        } else {
            log.error("id: {}", id);
            throw new Exception("Test throw new exception ");
        }
        return loanInfoModel;
    }
}
