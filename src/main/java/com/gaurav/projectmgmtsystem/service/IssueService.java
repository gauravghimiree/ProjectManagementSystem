package com.gaurav.projectmgmtsystem.service;

import com.gaurav.projectmgmtsystem.model.Issue;
import com.gaurav.projectmgmtsystem.model.User;
import com.gaurav.projectmgmtsystem.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {
  Issue getIssueById(long IssueId) throws Exception;

    List<Issue> getIssueByProjectId(long ProjectId) throws Exception;

    Issue CreateIssue(IssueRequest issue , User user) throws Exception;








  void  DeleteIssue(long issueId, long UserId) throws Exception;

  Issue addUserToIssue(Long issueId, Long UserId) throws Exception;

    Issue UpdateStatus(Long issueId, String status) throws Exception;



}
