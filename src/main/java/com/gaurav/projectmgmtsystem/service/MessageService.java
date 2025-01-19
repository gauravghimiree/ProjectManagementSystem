package com.gaurav.projectmgmtsystem.service;

import com.gaurav.projectmgmtsystem.model.Message;

import java.util.List;

public interface MessageService {


    Message sendMessage(Long userId, Long chatId, String Content) throws Exception;
    List<Message> getMessagesByProjectId(Long ProjectId) throws Exception;


}
