package ru.skypro.homework.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.UserEntity;

import java.util.List;

public interface AdRepository extends JpaRepository<AdEntity, Long> {

    List<AdEntity> findAll();
    AdEntity getReferenceById(int id);
    AdEntity findById(int id);
    void deleteById(int id);
    AdEntity saveAndFlush(AdEntity ad);

}
