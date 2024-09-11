package com.mian.ProjectMangementTool.controller;

import com.mian.ProjectMangementTool.model.Chat;
import com.mian.ProjectMangementTool.model.Invitation;
import com.mian.ProjectMangementTool.model.Project;
import com.mian.ProjectMangementTool.model.User;
import com.mian.ProjectMangementTool.request.InviteRequest;
import com.mian.ProjectMangementTool.response.MessageResponse;
import com.mian.ProjectMangementTool.service.InvitationService;
import com.mian.ProjectMangementTool.service.ProjectService;
import com.mian.ProjectMangementTool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private InvitationService invitationService;
    @GetMapping()

    public ResponseEntity<List<Project>> getProjects(
                                                    @RequestParam(required = false) String category,
                                                     @RequestParam(required = false) String tag,
                                                     @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.getProjectByTeam(user, category, tag);
        return new ResponseEntity<>(projects, HttpStatus.OK);

    }
    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(
                                                        @PathVariable Long projectId,
                                                        @RequestHeader("Authorization") String jwt
                                                            )  throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project project = projectService.getProjectById(projectId);
        return new ResponseEntity<>(project,HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Project> createProject(
                                                    @PathVariable Project project,
                                                    @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Project createdProject = projectService.createProject(project,user);

        return  new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }
    @PatchMapping()
    public ResponseEntity<Project> updateProject(
                                                    @PathVariable Long projectId,
                                                    @PathVariable Project project,
                                                    @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Project updateProject = projectService.updateProject(project,projectId);
        return new ResponseEntity<>(updateProject,HttpStatus.OK);
    }
    @DeleteMapping()
    public ResponseEntity<MessageResponse> deleteProject(
                                                    @PathVariable Long projectId,
                                                    @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId,user.getId());

        MessageResponse res = new MessageResponse("Project deleted Successfully");

        return  new ResponseEntity<>(res,HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProject(
                                                    @RequestParam(required = false) String keyword,
                                                    @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.seacrchProject(keyword,user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }
    @GetMapping("/{projectId}/chat")
    public ResponseEntity<Chat> getChatProjectId(
                                                        @PathVariable Long projectId,
                                                        @RequestHeader("Authorization") String jwt
    ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Chat projectChat = projectService.getChatByProjectId(projectId);
        return new ResponseEntity<>(projectChat,HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> inviteProject(
                                                        @RequestHeader("Authorization") String jwt,
                                                        @RequestBody Project project,
                                                        @RequestBody InviteRequest inviteRequest
            ) throws Exception{

        User user = userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(inviteRequest.getEmail(),inviteRequest.getProjectId());
        MessageResponse res = new MessageResponse("User invitation Sent");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @GetMapping("/accept_invitation")
    public ResponseEntity<Invitation> acceptInviteProject(
                                                                @RequestParam String token,
                                                                @RequestHeader("Authorization") String jwt,
                                                                @RequestBody Project project
    ) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation= invitationService.acceptInvitation(token,user.getId());

        projectService.addUserToProject(invitation.getProjectId(),user.getId());

        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED);
    }


}
