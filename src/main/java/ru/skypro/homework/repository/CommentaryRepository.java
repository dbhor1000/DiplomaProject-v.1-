package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Commentary;

import java.util.List;

public interface CommentaryRepository extends JpaRepository<Commentary, Long> {

    List<Commentary> findAll();
}
