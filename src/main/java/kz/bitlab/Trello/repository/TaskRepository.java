package kz.bitlab.Trello.repository;

import jakarta.transaction.Transactional;
import kz.bitlab.Trello.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Tasks, Long> {
    public List<Tasks> findByFolderId(Long id);
}
