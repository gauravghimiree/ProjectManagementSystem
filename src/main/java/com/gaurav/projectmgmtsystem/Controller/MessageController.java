package com.gaurav.projectmgmtsystem.Controller;


import com.gaurav.projectmgmtsystem.model.Chat;
import com.gaurav.projectmgmtsystem.model.Message;
import com.gaurav.projectmgmtsystem.model.User;
import com.gaurav.projectmgmtsystem.request.CreateMessageRequest;
import com.gaurav.projectmgmtsystem.service.MessageService;
import com.gaurav.projectmgmtsystem.service.ProjectService;
import com.gaurav.projectmgmtsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/send")
    public ResponseEntity <Message> sendMessage(@RequestBody CreateMessageRequest request)
        throws Exception {
        User user = userService.findUserById(request.getSenderId());
        if (user == null) {
            throw new Exception("User not found");
        }

        Chat chats = projectService.getProjectById(request.getProjectId()).getChat();
        if(chats == null) {
            throw new Exception("Chat not found");
        }

        Message sentMessage = messageService.sendMessage(request.getSenderId(),request.getProjectId(),request.getContent());
        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>>getMessageByChatId(@PathVariable Long projectId)
        throws Exception {
        List<Message>messages=messageService.getMessagesByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }

}
