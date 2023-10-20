package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.Commentary;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentaryRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.mapping.CommentaryMapping;

import java.util.Optional;


@Service
public class CommentServiceImpl implements CommentService {

    private final AdRepository adRepository;
    private final CommentaryMapping commentaryMapping;
    private final CommentaryRepository commentaryRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(AdRepository adRepository, CommentaryMapping commentaryMapping, CommentaryRepository commentaryRepository, UserRepository userRepository) {
        this.adRepository = adRepository;
        this.commentaryMapping = commentaryMapping;
        this.commentaryRepository = commentaryRepository;
        this.userRepository = userRepository;
    }

    //Функционал на фронтенде:
    //1. Получение всех комментариев объявления (через репозиторий) - GET - DTO: Comments
    //2. Добавление комментария к объявление - POST - DTO: CreateOrUpdateComment
    //3. Удаление комментария по id - DELETE
    //4. Обновление комментария по id (+id объявления) - PATCH - DTO: CreateOrUpdateComment

    @Override
    public Comments getCommentsOfOneAd(int adId) {
        if (adRepository.getReferenceById(adId) != null) {
            AdEntity adToRetrieveCommentsFrom = adRepository.getReferenceById(adId);
            Comments retrievedComments = commentaryMapping.adCommentsToCommentsDTO(adToRetrieveCommentsFrom);
            return retrievedComments;
        } else {
            throw new AdNotFoundException();
        }
    }

    @Override
    public Comment addCommentToAd(CreateOrUpdateComment commentToAdd, int adId) {

        if (adRepository.getReferenceById(adId) != null) {

            AdEntity adToAddCommentTo = adRepository.getReferenceById(adId);
            Commentary mappedInputDTO = commentaryMapping.createOrUpdateCommentDtoToCommentaryEntity(commentToAdd);
            mappedInputDTO.setUserRelated(adToAddCommentTo.getUserRelated());
            mappedInputDTO.setAdRelated(adToAddCommentTo);
            commentaryRepository.save(mappedInputDTO);
            return commentaryMapping.commentEntityToCommentDto(mappedInputDTO);
        } else {
            throw new AdNotFoundException();
        }
    }

    @Override
    public boolean deleteCommentByIdAndAdId(int adId, Integer commentId, String username) {

        AdEntity adFound = adRepository.getReferenceById(adId);
        Commentary commentFound = commentaryRepository.findByAdRelatedAndId(adFound, commentId);
        UserEntity userWhoCommented = commentFound.getUserRelated();
        UserEntity authorizedUser = userRepository.findByUsername(username);
        Role authorizedUserRole = authorizedUser.getRole();

        if ((Optional.of(adFound).isPresent() && Optional.of(commentFound).isPresent() && authorizedUserRole == Role.USER && userWhoCommented == authorizedUser) || (Optional.of(adFound).isPresent() && Optional.of(commentFound).isPresent() && authorizedUserRole == Role.ADMIN)) {

            commentaryRepository.deleteById(commentFound.getId());
            commentaryRepository.flush();
            return true;
            } else {

                throw new CommentNotFoundException();
        }
    }

    @Override
    public boolean patchCommentByIdAndAdId(int adId, Integer commentId, CreateOrUpdateComment createOrUpdateComment, String username) {

        AdEntity adFound = adRepository.getReferenceById(adId);
        Commentary commentFound = commentaryRepository.findByAdRelatedAndId(adFound, commentId);
        UserEntity userWhoCommented = commentFound.getUserRelated();
        UserEntity authorizedUser = userRepository.findByUsername(username);
        Role authorizedUserRole = authorizedUser.getRole();

        if ((Optional.of(adFound).isPresent() && Optional.of(commentFound).isPresent() && authorizedUserRole == Role.USER && userWhoCommented == authorizedUser) || (Optional.of(adFound).isPresent() && Optional.of(commentFound).isPresent() && authorizedUserRole == Role.ADMIN)) {

            commentFound.setText(createOrUpdateComment.getText());
            commentaryRepository.save(commentFound);
            return true;
        } else {
            throw new CommentNotFoundException();
        }
    }

}
