package com.gaurav.projectmgmtsystem.service;


import com.gaurav.projectmgmtsystem.model.Comments;
import com.gaurav.projectmgmtsystem.model.Issue;
import com.gaurav.projectmgmtsystem.model.User;
import com.gaurav.projectmgmtsystem.repository.CommentRepository;
import com.gaurav.projectmgmtsystem.repository.IssueRepository;
import com.gaurav.projectmgmtsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImp implements  CommentService{


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private UserRepository userRepository;


    public Comments CreateComment(Long issueId, Long userId, String content) throws Exception {
        Optional<Issue> issueOptional= issueRepository.findById(issueId);
        Optional<User> userOptional= userRepository.findById(userId);
        if(issueOptional.isEmpty())
        {
            throw new Exception("Issue not found");
        }
        if (userOptional.isEmpty())
        {
            throw new Exception("User not found");
        }
        Issue issue = issueOptional.get();
        User user = userOptional.get();
        Comments comments = new Comments();
        comments.setIssue(issue);
        comments.setUser(user);
        comments.setContent(content);
        comments.setCreatedDateTime(LocalDate.from(LocalDateTime.now()));
        Comments SavedComment = commentRepository.save(comments);

        issue.getComments().add(SavedComment);
        return SavedComment;

    }


    @Override
    public void deleteComment(Long CommentId,Long UserId)
     throws Exception{
       Optional<Comments> commentsOptional=commentRepository.findById(CommentId);
        Optional <User> userOptional  = userRepository.findById(UserId);
        if (commentsOptional.isEmpty()) {
            throw new Exception("comment not found with this id ");

        }
        if(userOptional.isEmpty())
        {
            throw new Exception("user not found with this id ");
        }

        Comments comments = commentsOptional.get();
        User user = userOptional.get();

        if(comments.getUser().equals(user)){
            commentRepository.delete(comments);
        }
        else
        {
            throw new Exception("user does not have permission to delete this comment ");
        }



    }

    @Override
    public List<Comments> findCommentByIssueId(Long IssueId)
    {
        return commentRepository.findCommentsByIssueId(IssueId);
    }

}
