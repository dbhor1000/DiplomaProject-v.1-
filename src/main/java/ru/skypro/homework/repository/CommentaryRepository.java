package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.Commentary;

import java.util.List;

public interface CommentaryRepository extends JpaRepository<Commentary, Long> {

    List<Commentary> findAll();
    Commentary findByAdRelatedAndId(AdEntity adRelated, int id);
    List<Commentary> findAllByAdRelated(AdEntity adRelated);
    void deleteAllInBatch();
    @Modifying
    @Query("delete from Commentary b where b.id=:id")
    void deleteById(int id);

}
