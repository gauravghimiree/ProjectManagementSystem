package com.gaurav.projectmgmtsystem.service;

import com.gaurav.projectmgmtsystem.model.Comments;
import lombok.Data;


import java.util.List;



public interface CommentService {
    public Comments CreateComment(Long issueId, Long userId, String content) throws Exception;
    public List<Comments> findCommentByIssueId(Long IssueId) ;
    public void deleteComment(Long CommentId,Long UserId) throws Exception ;

}
