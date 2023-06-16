package kz.bitlab.Trello.service;

import kz.bitlab.Trello.entity.Folders;
import kz.bitlab.Trello.entity.TaskCategories;
import kz.bitlab.Trello.entity.Tasks;
import kz.bitlab.Trello.repository.FoldersRepository;
import kz.bitlab.Trello.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoldersService {

    private final FoldersRepository foldersRepository;
    private final TaskRepository taskRepository;

    public final List<Folders> getAllFolders() {
        return foldersRepository.findAll();
    }
    public final Folders getFolderById(Long id) {
        return foldersRepository.getOne(id);
    }
//    public final List<TaskCategories> getAllCategoriesOnFolder(Long id) {
//        return foldersRepository.getOne(id).getCategoriesList();
//    }
//    public final Folders getAllTasksByFolderId(Long id) {
//        return foldersRepository.findByIdAndTasksListIsNotNull(id);
//    }
    public final void addFolder(String name) {
        if(name !=null) {
            Folders folder = new Folders();
            folder.setName(name);
            foldersRepository.save(folder);
        } else {
            System.out.println("ERROR ADDING FOLDER");
        }
    }
    public final void changeFolder(Long id, String name) {
        if (id != 0 || name != null) {
            Folders folders = foldersRepository.getOne(id);
            folders.setName(name);
            foldersRepository.save(folders);
        } else {
            System.out.println("ERROR DURING CHANGING FOLDER");
        }
    }
    public final void deleteFolder(Long id) {
        if(id != 0) {
            List<Tasks> tasksList = taskRepository.findByFolderId(id);
            Folders folders = foldersRepository.getOne(id);
            if (tasksList.size() > 0) {
                for (Tasks c : tasksList) {
                    if (c.getFolder().getId() == folders.getId()) {
                        taskRepository.delete(c);
                    }
                }
            }
            foldersRepository.deleteById(id);
        } else {
            System.out.println("ERROR DURING DELETING FOLDER!");
        }
    }
}
