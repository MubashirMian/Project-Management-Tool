package com.mian.ProjectMangementTool.controller;

import com.mian.ProjectMangementTool.config.JwtProvider;
import com.mian.ProjectMangementTool.model.User;
import com.mian.ProjectMangementTool.repository.UserRepository;
import com.mian.ProjectMangementTool.request.LoginRequest;
import com.mian.ProjectMangementTool.response.AuthResponse;
import com.mian.ProjectMangementTool.service.imp.CustomUserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailImpl customUserDetails;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        User isExistingUser = userRepository.findByEmail(user.getEmail());
        if (isExistingUser != null) {
            throw new Exception("The provided email is already in use");
        }

        User createUser = new User();

        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createUser.setEmail(user.getEmail());
        createUser.setFullName(user.getFullName());

        User savedUser = userRepository.save(createUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse res = new AuthResponse(); // creating response for authentication
        res.setMessage("Signup success");
        res.setJwt(jwt);

        return new ResponseEntity<>(res, HttpStatus.CREATED);

    }
    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> signing(@RequestBody LoginRequest loginRequest){

        String userName = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(userName,password); // invoke the authentication method to validate and verify credentials
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication); // return's generated token along with an expiry of 24 hours
        AuthResponse res = new AuthResponse();

        res.setJwt(jwt);
        res.setMessage("Logged In successfully");

        return new ResponseEntity<>(res,HttpStatus.CREATED);

    }

    private Authentication authenticate(String userName, String password) {
        UserDetails  userDetails = customUserDetails.loadUserByUsername(userName);

        if(userDetails == null ){ // if nothing is provided as username
            throw new BadCredentialsException("Invalid Username");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){ // if provided password doesn't match encoded password
            throw new BadCredentialsException(" Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

}
