package com.mian.ProjectMangementTool.service.imp;

import com.mian.ProjectMangementTool.model.Invitation;
import com.mian.ProjectMangementTool.repository.InvitationRepository;
import com.mian.ProjectMangementTool.service.EmailService;
import com.mian.ProjectMangementTool.service.InvitationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public void sendInvitation(String email, Long projectId) throws MessagingException {

        String invitationToken = UUID.randomUUID().toString(); // generate unique token ; FOR TRACKING AND SECURITY PURPOSES

        Invitation invitation = new Invitation();

        invitation.setEmail(email); // setting up required information for invitation
        invitation.setId(projectId);
        invitation.setToken(invitationToken);

        invitationRepository.save(invitation);
        String invitationLink = "http://localhost:5173/accept_invitation?token"+invitationToken; // upon clicking user would se be sent here
        emailService.sendEmailWithToken(email,invitationLink);
    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) throws Exception {
        Invitation invitation = invitationRepository.findByToken(token);

        if(invitation == null){ // in case the token doesn't exists
            throw new Exception("Invalid Invitation Token");
        }
        return null;
    }

    @Override
    public String getTokenByUserMail(String userEmail) {
        Invitation invitation = invitationRepository.findByEmail(userEmail);
        return invitation.getToken();
    }

    @Override
    public void deleteToken(String token) {
        Invitation invitation = invitationRepository.findByToken(token);
        invitationRepository.delete(invitation);

    }
}
