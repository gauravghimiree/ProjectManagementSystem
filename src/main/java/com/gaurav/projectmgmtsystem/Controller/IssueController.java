package com.gaurav.projectmgmtsystem.Controller;


import com.gaurav.projectmgmtsystem.model.Issue;
import com.gaurav.projectmgmtsystem.service.IssueService;
import com.gaurav.projectmgmtsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
}

