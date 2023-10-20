package ru.skypro.homework.service.mapping;

import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.Commentary;

import java.util.*;

public interface CommentaryMapping {

    public Comment commentEntityToCommentDto(Commentary commentary);
    public List<Comment> listFromCommentEntityToDto(List<Commentary> inputCommentsList);
    public Comments adCommentsToCommentsDTO(AdEntity ad);
    public Commentary createOrUpdateCommentDtoToCommentaryEntity(CreateOrUpdateComment createOrUpdateComment);
}
