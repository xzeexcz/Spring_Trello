package kz.bitlab.Trello.repository;

import jakarta.transaction.Transactional;
import kz.bitlab.Trello.entity.TaskCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface CategoriesRepository extends JpaRepository<TaskCategories, Long> {
    public List<TaskCategories> findAllById(Long id);
    @Modifying
    @Query(value = "DELETE FROM t_folders_categories_list WHERE categories_list_id =:id", nativeQuery = true)
    public void deleteCategories(Long id);
}
