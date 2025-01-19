package com.gaurav.projectmgmtsystem.service;

import com.gaurav.projectmgmtsystem.model.Chat;
import com.gaurav.projectmgmtsystem.model.Message;
import com.gaurav.projectmgmtsystem.model.User;
import com.gaurav.projectmgmtsystem.repository.MessageRepository;
import com.gaurav.projectmgmtsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private ProjectService projectService;

    @Override
    public Message sendMessage(Long senderId, Long projectId, String Content) throws Exception {
        User sender =userRepository.findById(senderId).orElseThrow(
                () -> new Exception("Sender not found")
        );
        Chat chat =projectService.getProjectById(projectId).getChat();
        Message message = new Message();
        message.setContent(Content);
        message.setSender(sender);
        message.setCreatedAt(LocalDateTime.now());
        message.setChat(chat);
        Message savedMessage = messageRepository.save(message);

        chat.getMessages().add(savedMessage);
        return savedMessage;
    }

    @Override
    public List<Message> getMessagesByProjectId(Long ProjectId) throws  Exception {
        Chat chat = projectService.getProjectById(ProjectId).getChat();
        List <Message> findByChatIdOrderByCreatedAtAsc=messageRepository.findByChatIdOrderByCreatedAtAsc (chat.getId());
        return findByChatIdOrderByCreatedAtAsc;

    }
}
