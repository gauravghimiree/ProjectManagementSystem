package com.gaurav.projectmgmtsystem.service;

import com.gaurav.projectmgmtsystem.model.Issue;
import com.gaurav.projectmgmtsystem.model.User;
import com.gaurav.projectmgmtsystem.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    Optional<Issue> getIssueById(int Issued) throws Exception;

    List<Issue> getIssueByProjectId(Long ProjectId) throws Exception;

    Issue CreateIssue(IssueRequest issue , Long UserId) throws Exception;





    Optional <Issue> DeleteIssue(Long issueId,long UserId) throws Exception;







    Issue addUserToIssue(Long issueId, Long UserId) throws Exception;

    Issue UpdateStatus(Long issueId, String status) throws Exception;



}
