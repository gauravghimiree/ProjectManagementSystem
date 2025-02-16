package com.gaurav.projectmgmtsystem.Controller;


import com.gaurav.projectmgmtsystem.model.Issue;
import com.gaurav.projectmgmtsystem.model.IssueDTO;
import com.gaurav.projectmgmtsystem.model.User;
import com.gaurav.projectmgmtsystem.request.IssueRequest;
import com.gaurav.projectmgmtsystem.response.AuthResponse;
import com.gaurav.projectmgmtsystem.service.IssueService;
import com.gaurav.projectmgmtsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;


    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById( @PathVariable Long  issueId) throws Exception {
        return ResponseEntity.ok(issueService.getIssueById(issueId));

    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity <List<Issue>> getIssueByProjectId(@PathVariable Long projectId)
            throws Exception {
        return ResponseEntity.ok(issueService.getIssueByProjectId(projectId))   ;
    }


    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueRequest issue  , @RequestHeader("Authorization") String token ) throws Exception
    {
        User tokenUser = userService.findUserProfileByJwt(token);
        User user = userService.findUserById(tokenUser.getId());


            Issue createdIssue =issueService.CreateIssue(issue, tokenUser );
            IssueDTO issueDTO = new IssueDTO();

            issueDTO.setDescription(createdIssue.getDescription());
            issueDTO.setDueDate(createdIssue.getDueDate());
            issueDTO.setId(createdIssue.getId());
            issueDTO.setPriority(createdIssue.getPriority());
            issueDTO.setProject(createdIssue.getProject());
            issueDTO.setProjectID(createdIssue.getProjectID());
            issueDTO.setStatus(createdIssue.getStatus());
            issueDTO.setTitle(createdIssue.getTitle());
            issueDTO.setTags(createdIssue.getTags());
            issueDTO.setAssignee(createdIssue.getAssignee());



            return ResponseEntity.ok(issueDTO);


    }


    @DeleteMapping("/{issueId}")
    public ResponseEntity<String> deleteIssue(
            @PathVariable Long issueId,
            @RequestHeader("Authorization") String token) throws Exception {
        // Find the user profile using the JWT
        User user = userService.findUserProfileByJwt(token);

        // Delete the issue with the provided issue ID and user ID
        issueService.DeleteIssue(issueId, user.getId());

        // Return a simple success message
        return ResponseEntity.ok("Issue Deleted Successfully");
    }


    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue>  AddUserToIssue(@PathVariable Long issueId, @PathVariable Long userId)
            throws Exception
    {
        Issue issue = issueService.addUserToIssue(issueId, userId);
        return ResponseEntity.ok(issue);

    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue>updateIssueStatus(
            @PathVariable String status,
            @PathVariable Long issueId
    )  throws  Exception
    {
        Issue issue =issueService.UpdateStatus(issueId, status);
        return ResponseEntity.ok(issue);
    }


}

