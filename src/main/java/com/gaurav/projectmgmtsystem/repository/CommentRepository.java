package com.gaurav.projectmgmtsystem.repository;

import com.gaurav.projectmgmtsystem.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {

    List<Comments> findCommentsByIssueId(Long issueId);
}
