package com.mian.ProjectMangementTool.repository;

import com.mian.ProjectMangementTool.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatIdOrderByCreatedAsc(Long chatId);
}
