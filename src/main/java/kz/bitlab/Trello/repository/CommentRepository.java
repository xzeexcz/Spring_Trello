package kz.bitlab.Trello.repository;

import jakarta.transaction.Transactional;
import kz.bitlab.Trello.entity.Comments;
import kz.bitlab.Trello.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comments, Long> {
    @Modifying
    @Query(value = "SELECT * FROM t_comments WHERE tasks_id=:id", nativeQuery = true)
    public List<Comments> findCommentsByTasksId(Long id);
    @Modifying
    @Query(value = "DELETE FROM t_comments WHERE tasks_id=:id", nativeQuery = true)
    public void deleteComments(Long id);
}
