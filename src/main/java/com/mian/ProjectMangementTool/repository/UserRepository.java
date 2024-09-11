package com.mian.ProjectMangementTool.repository;

import com.mian.ProjectMangementTool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
