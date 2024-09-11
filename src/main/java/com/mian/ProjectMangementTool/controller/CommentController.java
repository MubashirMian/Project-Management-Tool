package com.mian.ProjectMangementTool.controller;

import com.mian.ProjectMangementTool.model.Comment;
import com.mian.ProjectMangementTool.model.User;
import com.mian.ProjectMangementTool.request.CreateCommentRequest;
import com.mian.ProjectMangementTool.response.MessageResponse;
import com.mian.ProjectMangementTool.service.CommentService;
import com.mian.ProjectMangementTool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @PostMapping()
    public ResponseEntity<Comment> createComment(
                                                    @RequestBody CreateCommentRequest req,
                                                    @RequestHeader("Authorization") String jwt)
            throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Comment createdComment = commentService.createComment(req.getIssueId(),user.getId(),req.getContent());
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(
                                                            @PathVariable Long commentId,
                                                            @RequestHeader("Authorization") String jwt)
        throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getId());
        MessageResponse res = new MessageResponse();
        res.setMessage("Comment is successfully deleted");
        return  new ResponseEntity<>(res,HttpStatus.OK);
    }
    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentByIssueId(
                                                                 @PathVariable Long issueId)
            throws Exception {
        List<Comment> comments = commentService.findCommentByIssueId(issueId);
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }


}
