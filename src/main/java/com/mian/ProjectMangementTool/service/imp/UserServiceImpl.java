package com.mian.ProjectMangementTool.service.imp;

import com.mian.ProjectMangementTool.config.JwtProvider;
import com.mian.ProjectMangementTool.model.User;
import com.mian.ProjectMangementTool.repository.UserRepository;
import com.mian.ProjectMangementTool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt); // extraction email from jwt token
        return findUserByEmail(email); // using custom method to find the user from the database
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("User not Found");
        }
        return user;

    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) { // in case of getting no matching results
            throw new Exception(" user not found");
        }
        return optionalUser.get();
    }

    @Override
    public User updateUsersProjectSize(User user, int number) {
        user.setProjectSize(user.getProjectSize()+number); // adding one to existing project size
        return userRepository.save(user);
    }
}
