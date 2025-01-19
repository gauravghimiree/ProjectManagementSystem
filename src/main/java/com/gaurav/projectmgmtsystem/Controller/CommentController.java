package com.gaurav.projectmgmtsystem.Controller;


import com.gaurav.projectmgmtsystem.model.Comments;
import com.gaurav.projectmgmtsystem.model.User;
import com.gaurav.projectmgmtsystem.request.CreateCommentRequest;
import com.gaurav.projectmgmtsystem.response.MessageResponse;
import com.gaurav.projectmgmtsystem.service.CommentService;
import com.gaurav.projectmgmtsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Comments> createComment(
            @RequestBody CreateCommentRequest req,
            @RequestHeader("Authorization")String jwt
            )  throws  Exception{
        User user=userService.findUserProfileByJwt(jwt);
        Comments createdComment =commentService.CreateComment(req.getIssueId(),user.getId(),
                req.getContent());
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);


    }

    @DeleteMapping
    public ResponseEntity<MessageResponse> deleteComment(
            @PathVariable long commentId,
            @RequestHeader("Authorization") String Jwt

    )throws  Exception{
        User user=userService.findUserProfileByJwt(Jwt);
        commentService.deleteComment(commentId,user.getId());
        MessageResponse res = new MessageResponse();
        res.setMessage("Comment deleted  Successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/issueId")
    public ResponseEntity<List<Comments>> getCommentsByIssueId(@PathVariable long issueId){
        List<Comments>comments=commentService.findCommentByIssueId(issueId);
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }

}
