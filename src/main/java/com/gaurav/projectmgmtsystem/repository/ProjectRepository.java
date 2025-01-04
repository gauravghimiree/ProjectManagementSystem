package com.gaurav.projectmgmtsystem.repository;

import com.gaurav.projectmgmtsystem.model.Project;
import com.gaurav.projectmgmtsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectRepository  extends JpaRepository<Project, Long> {


    @Query("SELECT p FROM Project p JOIN p.team t WHERE t = :user AND p.name LIKE %:partialName%")
    List<Project> findByTeamContainingAndTeamContains(String partialName , User user);


    List<Project> findByTeamContainingOrOwner(User user,User owner);






}
