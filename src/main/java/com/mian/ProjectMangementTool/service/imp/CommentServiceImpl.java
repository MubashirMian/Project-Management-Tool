package com.mian.ProjectMangementTool.service.imp;

import com.mian.ProjectMangementTool.model.Comment;
import com.mian.ProjectMangementTool.model.Issue;
import com.mian.ProjectMangementTool.model.User;
import com.mian.ProjectMangementTool.repository.CommentRepository;
import com.mian.ProjectMangementTool.repository.IssueRepository;
import com.mian.ProjectMangementTool.repository.UserRepository;
import com.mian.ProjectMangementTool.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Override
    public Comment createComment(Long issueId, Long userId, String content) throws Exception {
        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        Optional<User> userOptional = userRepository.findById(userId);

        if(issueOptional.isEmpty()){
            throw new Exception("Issue not found");
        }
        if(userOptional.isEmpty()){
            throw new Exception("User not Found");
        }
        Issue issue = issueOptional.get();
        User user = userOptional.get();

        Comment comment = new Comment();

        comment.setContent(content);
        comment.setCreatedDateTime(LocalDateTime.now());
        comment.setUser(user);
        comment.setIssue(issue);

        Comment savedComment = commentRepository.save(comment);
        issue.getComments().add(savedComment);
        return savedComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        Optional<User> userOptional = userRepository.findById(userId);

        if(commentOptional.isEmpty()){
            throw new Exception("Comment not Found");
        }
        if(userOptional.isEmpty()){
            throw new Exception("User not found with id"+userId);
        }

        Comment comment = commentOptional.get();
        User user = userOptional.get();

        if(comment.getUser().equals(user)){
            commentRepository.delete(comment);
        } else{
            throw new Exception("User is not authorized to delete that comment!");
        }
    }

    @Override
    public List<Comment> findCommentByIssueId(Long issueId) throws Exception {
        return commentRepository.findCommentByIssueId(issueId);
    }
}
