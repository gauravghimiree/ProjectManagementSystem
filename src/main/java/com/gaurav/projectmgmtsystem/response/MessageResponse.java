package com.gaurav.projectmgmtsystem.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




public class MessageResponse {
    private String message;


    public MessageResponse() {
    }

    // All-args constructor
    public MessageResponse(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
