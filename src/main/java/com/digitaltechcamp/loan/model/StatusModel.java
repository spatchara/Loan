package com.digitaltechcamp.loan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StatusModel implements Serializable {

    @JsonProperty("code") //do for json
    private String code;  //constructer do overload

    @JsonProperty("message")
    private String message;

    public  StatusModel(){

    }
    public StatusModel(String code, String message){
        this.code = code;
        this.message = message;
    }
}
