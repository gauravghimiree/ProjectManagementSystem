package com.gaurav.projectmgmtsystem.repository;

import com.gaurav.projectmgmtsystem.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

}
