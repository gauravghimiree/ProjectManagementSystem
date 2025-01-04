package com.gaurav.projectmgmtsystem.service;

import com.gaurav.projectmgmtsystem.model.Invitation;

public interface InvitationService {

    void sendInvitation(String email, long projectId) throws Exception;

    Invitation acceptInvitation(String token, long userId) throws Exception;

    String getTokenByUserMail(String userEmail);

    void deleteToken(String token);
}
