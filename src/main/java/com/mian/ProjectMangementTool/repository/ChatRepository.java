package com.mian.ProjectMangementTool.repository;

import com.mian.ProjectMangementTool.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat,Long> {
}
