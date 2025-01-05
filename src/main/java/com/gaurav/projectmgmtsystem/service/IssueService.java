package com.gaurav.projectmgmtsystem.service;

import com.gaurav.projectmgmtsystem.model.Issue;
import com.gaurav.projectmgmtsystem.model.User;
import com.gaurav.projectmgmtsystem.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {
  Issue getIssueById(Long IssueId) throws Exception;

    List<Issue> getIssueByProjectId(Long ProjectId) throws Exception;

    Issue CreateIssue(IssueRequest issue , User user) throws Exception;





    void  DeleteIssue(Long issueId,long UserId) throws Exception;







    Issue addUserToIssue(Long issueId, Long UserId) throws Exception;

    Issue UpdateStatus(Long issueId, String status) throws Exception;



}
