package com.gaurav.projectmgmtsystem.service;

import com.gaurav.projectmgmtsystem.model.Issue;
import com.gaurav.projectmgmtsystem.model.Project;
import com.gaurav.projectmgmtsystem.model.User;
import com.gaurav.projectmgmtsystem.repository.IssueRepository;
import com.gaurav.projectmgmtsystem.request.IssueRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements  IssueService {




   @Autowired
    private IssueRepository issueRepository;

   @Autowired
   private  ProjectService projectService;

   @Autowired
   private  UserService userService;

    @Override
    public Issue getIssueById(Long issueId) throws Exception {
       Optional<Issue> issue = issueRepository.findById(issueId);
       if (issue.isPresent()) {
           return issue.get();
       }

       throw new Exception( "No Issues find with issueid " + issueId);
    }

    @Override
    public List<Issue> getIssueByProjectId(Long ProjectId) throws Exception {
        return issueRepository.findByProjectId(ProjectId);
    }

    @Override
    public Issue CreateIssue(IssueRequest issueRequest, User user) throws Exception {
        Project project = projectService.getProjectById(issueRequest.getProjectId());
        Issue issue = new Issue();
        issue.setTitle(issueRequest.getTitle());
        issue.setDescription(issueRequest.getDescription());
        issue.setStatus(issueRequest.getStatus());
        issue.setProjectID(issueRequest.getProjectId());
        issue.setPriority(issueRequest.getPriority());
        issue.setDueDate(issueRequest.getDueDate());
        issue.setProject(project);
        return issueRepository.save(issue);
    }

    @Override
    public void  DeleteIssue(Long issueId, long UserId) throws Exception {
        getIssueById(issueId);
      issueRepository.deleteById(issueId);
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long UserId) throws Exception {
        User user =userService.findUserById(UserId);
        Issue issue=getIssueById(issueId);

        issue.setAssignee(user);
        return issueRepository.save(issue);
    }

    @Override
    public Issue UpdateStatus(Long issueId, String status) throws Exception {
       Issue issue = getIssueById(issueId);

       issue.setStatus(status);

       return issueRepository.save(issue);
    }
}
