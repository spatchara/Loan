package com.digitaltechcamp.loan.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //Snake case
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoanInfoModel {
    private Long id;
    private String status;
    private String accountPayable;
    private String AccountReceivable;
    private double PrincipleAmount;
}
