package kz.bitlab.Trello.repository;

import jakarta.transaction.Transactional;
import kz.bitlab.Trello.entity.Folders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface FoldersRepository extends JpaRepository<Folders, Long> {
    List<Folders> findAllById(Long id);
}
