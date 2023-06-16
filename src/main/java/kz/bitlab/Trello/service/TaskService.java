package kz.bitlab.Trello.service;

import kz.bitlab.Trello.entity.Comments;
import kz.bitlab.Trello.entity.Tasks;
import kz.bitlab.Trello.repository.CommentRepository;
import kz.bitlab.Trello.repository.FoldersRepository;
import kz.bitlab.Trello.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final FoldersRepository foldersRepository;
    private final CommentRepository commentRepository;
    public final List<Tasks> getTasksByFolderId(Long id) {
        return taskRepository.findByFolderId(id);
    }
    public final Tasks getTaskById(Long id) {
        return taskRepository.getOne(id);
    }
    public final void addTask(String task_name, String task_description, Long folder_id) {
        if(task_name == null && task_description ==null) {
            System.out.println("ERROR DURING ADDING TASK");
        } else {
            Tasks task = new Tasks();
            task.setDescription(task_description);
            task.setTitle(task_name);
            task.setStatus(0);
            task.setFolder(foldersRepository.getOne(folder_id));
            taskRepository.save(task);
        }
    }
    public final void changeTask(String name, String description, Long task_id, int status) {
        if(name!=null || description !=null || task_id!=0 || status !=0) {
            Tasks tasks = taskRepository.getOne(task_id);
            tasks.setTitle(name);
            tasks.setDescription(description);
            tasks.setStatus(status);
            taskRepository.save(tasks);
        } else {
            System.out.println("ERROR DURING CHANGING TASK!");
        }
    }
    public final void deleteTask(Long id) {
        if(id!=0) {
            List<Comments> comments = commentRepository.findCommentsByTasksId(id);
            if(comments!=null) {
                commentRepository.deleteComments(id);
                taskRepository.deleteById(id);
            } else {
                taskRepository.deleteById(id);
            }
        } else {
            System.out.println("ERROR DURING DELETING TASK!");
        }
    }
}
