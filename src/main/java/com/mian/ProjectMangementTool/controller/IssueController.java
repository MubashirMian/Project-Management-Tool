package com.mian.ProjectMangementTool.controller;

import com.mian.ProjectMangementTool.dto.IssueDto;
import com.mian.ProjectMangementTool.model.Issue;
import com.mian.ProjectMangementTool.model.User;
import com.mian.ProjectMangementTool.request.IssueRequest;
import com.mian.ProjectMangementTool.response.AuthResponse;
import com.mian.ProjectMangementTool.response.MessageResponse;
import com.mian.ProjectMangementTool.service.IssueService;
import com.mian.ProjectMangementTool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apo/issue")
public class IssueController {
    @Autowired
    private IssueService issueService;
    @Autowired
    private UserService userService;
    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(
                                                @PathVariable Long issueId
    ) throws Exception{
        return ResponseEntity.ok(issueService.getIssueById(issueId));
    }

    @PostMapping()
    public ResponseEntity<IssueDto> createIssue(
                                                    @RequestBody IssueRequest issueRequest,
                                                    @RequestHeader("Authorization") String jwt
            ) throws Exception{

        User tokenUser = userService.findUserProfileByJwt(jwt); // from header using the token to find user
        User user = userService.findUserById(tokenUser.getId()); // finding the user from extracted info from token


            Issue createdIssue = issueService.createIssue(issueRequest,tokenUser); // initializing the actual entity for TASK/ISSUE
            IssueDto issueDto = new IssueDto();  //  initializing object for DTO class

            issueDto.setDescription(createdIssue.getDescription()); // setting the information for task/issue
            issueDto.setId(createdIssue.getId());
            issueDto.setPriority(createdIssue.getPriority());
            issueDto.setAssignee(createdIssue.getAssignee());
            issueDto.setStatus(createdIssue.getStatus());
            issueDto.setTitle(createdIssue.getTitle());
            issueDto.setTags(createdIssue.getTags());
            issueDto.setDueDate(createdIssue.getDueDate());
            issueDto.setProjectId(createdIssue.getProjectId());
            issueDto.setProject(createdIssue.getProject());

            return ResponseEntity.ok(issueDto);

    }
    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(
                                                        @PathVariable Long issueId,
                                                        @RequestHeader("Authorization") String jwt)
        throws Exception{

        User user = userService.findUserProfileByJwt(jwt);
        issueService.deleteIssue(issueId,user.getId());

        MessageResponse res = new MessageResponse();
        res.setMessage("Issue Deleted Successfully");

        return ResponseEntity.ok(res);

    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(
                                                    @PathVariable Long issueId,
                                                    @PathVariable Long userId
    ) throws Exception {
        Issue issue = issueService.addUserToIssue(issueId, userId);
        return  ResponseEntity.ok(issue);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(
                                                        @PathVariable String status,
                                                        @PathVariable Long issueId
    ) throws Exception{
        Issue issue = issueService.updateStatus(issueId,status);
        return ResponseEntity.ok(issue);
    }

}
