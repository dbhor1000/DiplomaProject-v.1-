package ru.skypro.homework.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.model.AdEntity;

import java.util.List;

public interface AdRepository extends JpaRepository<AdEntity, Long> {

    List<AdEntity> findAll();
    AdEntity getReferenceById(int id);
    AdEntity findById(int id);
    @Modifying
    @Query("delete from AdEntity b where b.id=:id")
    void deleteById(int id);
    AdEntity saveAndFlush(AdEntity ad);

}
