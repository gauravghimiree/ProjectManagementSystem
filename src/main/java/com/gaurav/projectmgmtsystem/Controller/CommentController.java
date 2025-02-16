package com.gaurav.projectmgmtsystem.Controller;

import com.gaurav.projectmgmtsystem.model.Comments;
import com.gaurav.projectmgmtsystem.model.User;
import com.gaurav.projectmgmtsystem.request.CreateCommentRequest;
import com.gaurav.projectmgmtsystem.response.MessageResponse;
import com.gaurav.projectmgmtsystem.service.CommentService;
import com.gaurav.projectmgmtsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        // Retrieve the user profile by JWT token
        User user = userService.findUserProfileByJwt(jwt);

        // Create the comment by passing necessary details including user ID
        Comments createdComment = commentService.CreateComment(req.getIssueId(), user.getId(), req.getContent());

        // Set the creator's name on the comment (this is done in the service layer)
        createdComment.setCreatorName(user.getFullName()); // Ensure the creator name is set here if not done in service

        // Return the created comment along with creator's name
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
            @PathVariable long commentId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        // Retrieve user profile by JWT
        User user = userService.findUserProfileByJwt(jwt);

        // Delete the comment
        commentService.deleteComment(commentId, user.getId());

        // Prepare response message
        MessageResponse res = new MessageResponse();
        res.setMessage("Comment deleted successfully");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comments>> getCommentsByIssueId(@PathVariable long issueId) {
        // Fetch comments for the specified issue
        List<Comments> comments = commentService.findCommentByIssueId(issueId);

        // Return the list of comments
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
