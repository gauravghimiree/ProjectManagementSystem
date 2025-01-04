package com.gaurav.projectmgmtsystem.service;


import org.springframework.stereotype.Service;


public interface EmailService {
    public void sendEmailWithToken(String userEmail,String link) throws Exception;

}
