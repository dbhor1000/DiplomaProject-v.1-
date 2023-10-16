package ru.skypro.homework.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.UserEntity;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {

    List<Ad> findAll();
    Ad getReferenceById(int id);
    Ad findById(int id);
    void deleteById(int id);
    Ad saveAndFlush(Ad ad);

}
