package com.mian.ProjectMangementTool.service.imp;

import com.mian.ProjectMangementTool.model.Chat;
import com.mian.ProjectMangementTool.model.Message;
import com.mian.ProjectMangementTool.model.User;
import com.mian.ProjectMangementTool.repository.MessageRepository;
import com.mian.ProjectMangementTool.repository.UserRepository;
import com.mian.ProjectMangementTool.service.MessageService;
import com.mian.ProjectMangementTool.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectService projectService;

    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) throws Exception {
        User sender = userRepository.findById(senderId)
                .orElseThrow(()->new Exception("User not found"));
        Chat chat = projectService.getChatByProjectId(projectId).getProject().getChat();

        Message message = new Message();

        message.setContent(content);
        message.setSender(sender);
        message.setChat(chat);
        message.setCreatedAt(LocalDateTime.now());

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessageByProjectId(Long projectId) throws Exception {
        Chat chat = projectService.getChatByProjectId(projectId);
        return messageRepository.findByChatIdOrderByCreatedAsc(chat.getId());
    }
}
