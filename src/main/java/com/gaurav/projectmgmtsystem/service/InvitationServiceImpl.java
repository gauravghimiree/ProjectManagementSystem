package com.gaurav.projectmgmtsystem.service;
import com.gaurav.projectmgmtsystem.model.Invitation;
import com.gaurav.projectmgmtsystem.repository.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class  InvitationServiceImpl implements InvitationService {

    @Autowired
    private InvitationRepository invitationRepository;


    @Autowired
    private EmailService emailService;





    @Override
    public void sendInvitation(String email, long projectId) throws Exception {
        String invitationToken = UUID.randomUUID().toString();
        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(invitationToken);
        invitationRepository.save(invitation);

        String invitationLink="http://localhost:5173/accept_invitation?token="+invitationToken;
        emailService.sendEmailWithToken(email,invitationLink);



    }

    @Override
    public Invitation acceptInvitation(String token, long userId) throws Exception {

        Invitation invitation = invitationRepository.findByToken(token);
        if(invitation==null) {
            throw new Exception("invalid invitation token");
        }


        return invitation;
    }

    @Override
    public String getTokenByUserMail(String userEmail) {
        Invitation inviataion = invitationRepository.findByEmail(userEmail);

        return inviataion.getToken();
    }

    @Override
    public void deleteToken(String token) {
        Invitation invitation = invitationRepository.findByToken(token);


      invitationRepository.delete(invitation);
    }
}
