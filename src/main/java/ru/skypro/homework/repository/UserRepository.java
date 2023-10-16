package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity getReferenceById(long id);
    UserEntity findByUsername(String username);
    UserEntity save(UserEntity userEntity);


}
