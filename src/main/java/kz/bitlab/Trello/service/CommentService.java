package kz.bitlab.Trello.service;

import kz.bitlab.Trello.entity.Comments;
import kz.bitlab.Trello.repository.CommentRepository;
import kz.bitlab.Trello.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    public final List<Comments> getAllComments(Long id) {
        return commentRepository.findCommentsByTasksId(id);
    }
    public final void addComment(String comment, Long id) {
        if(comment !=null || id !=0) {
            Comments comments = new Comments();
            comments.setComment(comment);
            comments.setTasks(taskRepository.findById(id).orElseThrow());
            commentRepository.save(comments);
        }
    }
}
