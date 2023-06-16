package kz.bitlab.Trello.service;

import kz.bitlab.Trello.entity.Folders;
import kz.bitlab.Trello.entity.TaskCategories;
import kz.bitlab.Trello.entity.Tasks;
import kz.bitlab.Trello.repository.CategoriesRepository;
import kz.bitlab.Trello.repository.FoldersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;
    private final FoldersRepository foldersRepository;
    public final List<TaskCategories> getAllCategories() {
        return categoriesRepository.findAll();
    }
    public final void deleteCategoriesOnFolder(Long[] idc, Long idf) {
        Folders folders = foldersRepository.findById(idf).orElseThrow();
        for(Long id : idc) {
            TaskCategories taskCategories = categoriesRepository.findById(id).orElseThrow();
            if(folders.getCategoriesList().size() > 0 && folders.getCategoriesList()!=null) {
                folders.getCategoriesList().remove(taskCategories);
            }
        }
        foldersRepository.save(folders);
    }
    public final void assignCategories(Long[] idc, Long idf) {
            Folders folders = foldersRepository.findById(idf).orElseThrow();
            if(folders.getCategoriesList()!=null && folders.getCategoriesList().size() > 0) {
                for(Long id: idc) {
                    TaskCategories taskCategories = categoriesRepository.findById(id).orElseThrow();
                    if(!folders.getCategoriesList().contains(taskCategories)) {
                        folders.getCategoriesList().add(taskCategories);
                    }
                }
            } else {
                List<TaskCategories> taskCategoriesList = new ArrayList<>();
                for (Long id : idc) {
                    TaskCategories taskCategories = categoriesRepository.findById(id).orElseThrow();
                    taskCategoriesList.add(taskCategories);
                }
                folders.setCategoriesList(taskCategoriesList);
            }
            foldersRepository.save(folders);
    }
    public final void deleteCategoriesFromDB(Long[] idc) {
        for(Long id: idc) {
            TaskCategories taskCategories = categoriesRepository.findById(id).orElse(null);
            if(taskCategories!=null) {
                categoriesRepository.deleteCategories(id);
                categoriesRepository.delete(taskCategories);
            }
        }
    }
    public final void addCategoriesDB(String name) {
        if(name!=null) {
            TaskCategories taskCategories = new TaskCategories();
            taskCategories.setName(name);
            if(taskCategories!=null) {
                categoriesRepository.save(taskCategories);
            } else {
                System.out.println("ERROR DURING ADING CATEGORY TO DATABASE!");
            }
        } else {
            System.out.println("ERROR DURING ADING CATEGORY TO DATABASE!");
        }
    }
}
