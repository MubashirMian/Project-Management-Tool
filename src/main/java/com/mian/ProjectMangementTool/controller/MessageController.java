package com.mian.ProjectMangementTool.controller;

import com.mian.ProjectMangementTool.model.Chat;
import com.mian.ProjectMangementTool.model.Message;
import com.mian.ProjectMangementTool.model.User;
import com.mian.ProjectMangementTool.request.CreateMessageRequest;
import com.mian.ProjectMangementTool.service.MessageService;
import com.mian.ProjectMangementTool.service.ProjectService;
import com.mian.ProjectMangementTool.service.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private MessageService messageService;

    public ResponseEntity<Message> sendMessage(
                                                @RequestBody CreateMessageRequest request
            ) throws Exception {
        User user = userService.findUserById(request.getSenderId());

        Chat chats = projectService.getProjectById(request.getProjectId()).getChat();
        if(chats == null) {
            throw new Exception("Chats not found");
        }
        Message sentMessage = messageService.sendMessage(
                request.getSenderId(),
                request.getProjectId(),
                request.getContent());
        return  ResponseEntity.ok(sentMessage);
    }
    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(
                                                                @PathVariable Long projectId
    ) throws Exception {
        List<Message> messages = messageService.getMessageByProjectId(projectId);
        return ResponseEntity.ok(messages);
    }
}
