package com.gaurav.projectmgmtsystem.repository;

import com.gaurav.projectmgmtsystem.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository  extends JpaRepository<Invitation, Long> {
    Invitation findByToken(String token);

    Invitation findByEmail(String useremail);
    //error resolved

}
