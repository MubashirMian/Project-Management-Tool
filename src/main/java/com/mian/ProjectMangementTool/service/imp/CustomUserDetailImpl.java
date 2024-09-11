package com.mian.ProjectMangementTool.service.imp;

import com.mian.ProjectMangementTool.model.User;
import com.mian.ProjectMangementTool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("User with this email not found "+ username);
        }
        List<GrantedAuthority> autthorities = new ArrayList<>();
        return  new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),autthorities);

    }
}
