package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.mapping.CommentaryMapping;


@Service
public class CommentServiceImpl implements CommentService {

    private final AdRepository adRepository;
    private final CommentaryMapping commentaryMapping;

    public CommentServiceImpl(AdRepository adRepository, CommentaryMapping commentaryMapping) {
        this.adRepository = adRepository;
        this.commentaryMapping = commentaryMapping;
    }

    @Override
    public Comments getCommentsOfOneAd(Long AdId) {
        if (adRepository.getReferenceById(AdId) != null) {
            Ad adToRetrieveCommentsFrom = adRepository.getReferenceById(AdId);
            Comments retrievedComments = commentaryMapping.adCommentsToCommentsDTO(adToRetrieveCommentsFrom);
            return retrievedComments;
        } else {
            throw new AdNotFoundException();
        }
    }

}
