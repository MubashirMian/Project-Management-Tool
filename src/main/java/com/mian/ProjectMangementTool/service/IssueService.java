package com.mian.ProjectMangementTool.service;

import com.mian.ProjectMangementTool.model.Issue;
import com.mian.ProjectMangementTool.model.User;
import com.mian.ProjectMangementTool.request.IssueRequest;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    Issue getIssueById(Long issueId) throws Exception;
    List<Issue> getIssueByProjectId(Long projectId) throws Exception;
    Issue createIssue(IssueRequest issue, User user) throws Exception;
    Optional<Issue> updateIssue(Long issueId, IssueRequest issue, Long userId) throws Exception;
    void deleteIssue(Long issueId, Long userId) throws Exception;
    Issue addUserToIssue(Long issueId, Long userId) throws Exception;
    Issue updateStatus(Long issueId, String status) throws Exception;

}
