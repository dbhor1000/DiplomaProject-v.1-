package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Commentary;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentaryRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.mapping.CommentaryMapping;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


@Service
public class CommentServiceImpl implements CommentService {

    private final AdRepository adRepository;
    private final CommentaryMapping commentaryMapping;
    private final CommentaryRepository commentaryRepository;

    public CommentServiceImpl(AdRepository adRepository, CommentaryMapping commentaryMapping, CommentaryRepository commentaryRepository) {
        this.adRepository = adRepository;
        this.commentaryMapping = commentaryMapping;
        this.commentaryRepository = commentaryRepository;
    }

    //Функционал на фронтенде:
    //1. Получение всех комментариев объявления (через репозиторий) - GET - DTO: Comments
    //2. Добавление комментария к объявление - POST - DTO: CreateOrUpdateComment
    //3. Удаление комментария по id - DELETE
    //4. Обновление комментария по id (+id объявления) - PATCH - DTO: CreateOrUpdateComment

    @Override
    public Comments getCommentsOfOneAd(Long adId) {
        if (adRepository.getReferenceById(adId) != null) {
            Ad adToRetrieveCommentsFrom = adRepository.getReferenceById(adId);
            Comments retrievedComments = commentaryMapping.adCommentsToCommentsDTO(adToRetrieveCommentsFrom);
            return retrievedComments;
        } else {
            throw new AdNotFoundException();
        }
    }

    @Override
    public Comment addCommentToAd(CreateOrUpdateComment commentToAdd, Long adId) {

        if (adRepository.getReferenceById(adId) != null) {

            Ad adToAddCommentTo = adRepository.getReferenceById(adId);

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
    public boolean deleteCommentByIdAndAdId(Integer adId, Integer commentId) {


        if (adRepository.getReferenceById(adId.longValue()) != null) {

            Ad adFound = adRepository.getReferenceById(adId.longValue());
            Commentary commentFound = commentaryRepository.findByAdRelatedAndId(adFound, commentId);
            commentaryRepository.deleteById(commentFound.getId());

            return true;

            } else {

                throw new CommentNotFoundException();

        }
    }

    @Override
    public boolean patchCommentByIdAndAdId(Integer adId, Integer commentId, CreateOrUpdateComment createOrUpdateComment) {


        if (adRepository.getReferenceById(adId.longValue()) != null) {

            Ad adFound = adRepository.getReferenceById(adId.longValue());
            Commentary commentFound = commentaryRepository.findByAdRelatedAndId(adFound, commentId);
            commentFound.setText(createOrUpdateComment.getText());
            commentaryRepository.save(commentFound);

            return true;

        } else {

            throw new CommentNotFoundException();

        }
    }

}
