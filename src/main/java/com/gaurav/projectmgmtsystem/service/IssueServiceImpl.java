package com.gaurav.projectmgmtsystem.service;

import com.gaurav.projectmgmtsystem.model.Issue;
import com.gaurav.projectmgmtsystem.request.IssueRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements  IssueService {


    @Override
    public Optional<Issue> getIssueById(int Issued) throws Exception {
       Optional<Issue> issue = IssueRepository.findById(issueId);
       if (issue.isPresent()) {
           return issue;
       }

       throw new Exception( "No Issues find with issueid " + issueId);
    }

    @Override
    public List<Issue> getIssueByProjectId(Long ProjectId) throws Exception {
        return List.of();
    }

    @Override
    public Issue CreateIssue(IssueRequest issue, Long UserId) throws Exception {
        return null;
    }

    @Override
    public Optional<Issue> DeleteIssue(Long issueId, long UserId) throws Exception {
        return Optional.empty();
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long UserId) throws Exception {
        return null;
    }

    @Override
    public Issue UpdateStatus(Long issueId, String status) throws Exception {
        return null;
    }
}
