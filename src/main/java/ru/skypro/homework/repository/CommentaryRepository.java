package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Commentary;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentaryRepository extends JpaRepository<Commentary, Long> {

    List<Commentary> findAll();
    Commentary findByAdRelatedAndId(Ad adRelated, int id);
    List<Commentary> findAllByAdRelated(Ad adRelated);
    void deleteAllInBatch();
    @Modifying
    @Query("delete from Commentary b where b.id=:id")
    void deleteById(int id);

}
