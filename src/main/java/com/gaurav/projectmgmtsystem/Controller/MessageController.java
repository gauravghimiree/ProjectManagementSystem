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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest request) throws Exception {
        User user = userService.findUserById(request.getSenderId());
        if (user == null) {
            throw new Exception("User not found");
        }

        Chat chat = projectService.getProjectById(request.getProjectId()).getChat();
        if (chat == null) {
            throw new Exception("Chat not found for this project");
        }

        Message sentMessage = messageService.sendMessage(request.getSenderId(), request.getProjectId(), request.getContent());
        return ResponseEntity.ok(sentMessage);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Map<String, Object>>> getMessageByChatId(@PathVariable Long projectId) throws Exception {
        List<Message> messages = messageService.getMessagesByProjectId(projectId);

        // Map each message into a custom response Map
        List<Map<String, Object>> response = messages.stream().map(message -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", message.getId());
            map.put("content", message.getContent());
            map.put("createdAt", message.getCreatedAt());
            // Check for sender nullity and set sender's exact name
            String senderName = (message.getSender() != null && message.getSender().getFullName() != null)
                    ? message.getSender().getFullName() : "Anonymous";
            map.put("senderName", senderName);
            return map;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
