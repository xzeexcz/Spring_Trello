package kz.bitlab.Trello.controllers;

import kz.bitlab.Trello.entity.Folders;
import kz.bitlab.Trello.entity.Tasks;
import kz.bitlab.Trello.service.CategoriesService;
import kz.bitlab.Trello.service.CommentService;
import kz.bitlab.Trello.service.FoldersService;
import kz.bitlab.Trello.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final CategoriesService categoriesService;
    private final CommentService commentService;
    private final FoldersService foldersService;
    private final TaskService taskService;


    @GetMapping(value = "/")
    public String none() {
        return "redirect:/home";
    }
    @GetMapping(value = "/home")
    public String homePage(Model model) {
        model.addAttribute("papki", foldersService.getAllFolders());
        return "home";
    }
    @GetMapping(value = "/details/{folder_id}")
    public String details(@PathVariable(name = "folder_id") Long id, Model model) {
        model.addAttribute("papka", foldersService.getFolderById(id));
        model.addAttribute("zadanka", taskService.getTasksByFolderId(id));
        model.addAttribute("cati", categoriesService.getAllCategories());
        return "details";
    }

    @PostMapping(value = "/add-folder")
    public String addFolderPage(@RequestParam(name = "folder_name") String name) {
        foldersService.addFolder(name);
        return "redirect:/home";
    }
    @PostMapping(value = "/change-folder")
    public String changePage(@RequestParam(name = "folder_id") Long id,
                             @RequestParam(name = "folder_rename") String rename) {
        foldersService.changeFolder(id,rename);
        return "redirect:/home";
    }
    @PostMapping(value = "/delete-folder")
    public String deletePage(@RequestParam(name = "folder_id") Long id) {
        foldersService.deleteFolder(id);
        return "redirect:/home";
    }
    @PostMapping(value = "/add-task")
    public String addTask(@RequestParam(name = "folder_id") Long folder_id,
                          @RequestParam(name = "task_name") String task_name,
                          @RequestParam(name = "task_descr") String task_description) {
        taskService.addTask(task_name, task_description, folder_id);
        return "redirect:/details/" + folder_id;
    }
    @GetMapping(value = "/details/tasks/{task_id}")
    public String tasks_Details(@PathVariable(name = "task_id") Long id, Model model) {
        model.addAttribute("zadanka", taskService.getTaskById(id));
        model.addAttribute("comenti", commentService.getAllComments(id));
        return "task_details";
    }
    @PostMapping(value = "/change-task")
    public String changeTask(@RequestParam(name = "task_rename") String task_rename,
                             @RequestParam(name = "task_redescr") String task_redescr,
                             @RequestParam(name = "task_id") Long task_id,
                             @RequestParam(name = "task_restatus") int task_restatus) {
        taskService.changeTask(task_rename,task_redescr, task_id, task_restatus);
        return "redirect:/details/tasks/"+task_id;
    }
    @PostMapping(value = "/delete-task")
    public String deleteTask(@RequestParam(name = "task_id") Long id) {
        taskService.deleteTask(id);
        return "redirect:/home";
    }
    @GetMapping(value = "/categories")
    public String categories(Model model) {
        model.addAttribute("vkladi", categoriesService.getAllCategories());
        return "categories";
    }
    @PostMapping(value = "/unassign-categories")
    public String deleteCategories(@RequestParam(name = "cat_id") Long[] idc,
                                   @RequestParam(name = "folder_id") Long idf) {
        categoriesService.deleteCategoriesOnFolder(idc,idf);
        return "redirect:/details/"+idf;
    }
    @PostMapping(value = "/details/assign-category")
    public String assignCategories(@RequestParam(name = "cat1_id") Long[] idc,
                                   @RequestParam(name = "folder_id") Long idf) {
        categoriesService.assignCategories(idc,idf);
        return "redirect:/details/"+idf;
    }
    @PostMapping(value = "/delete-categories-db")
    public String deleteCategoriesFromDB(@RequestParam(name = "cat_id") Long[] idc) {
        categoriesService.deleteCategoriesFromDB(idc);
        return "redirect:/categories";
    }
    @PostMapping(value = "/add-category-db")
    public String addCategory(@RequestParam(name = "category_name") String name) {
        categoriesService.addCategoriesDB(name);
        return "redirect:/categories";
    }
    @PostMapping(value = "/add-comment")
    public String addComment(@RequestParam(name = "comment") String comment,
                             @RequestParam(name = "task_id") Long id) {
        commentService.addComment(comment,id);
        return "redirect:/details/tasks/"+id;
    }
}
