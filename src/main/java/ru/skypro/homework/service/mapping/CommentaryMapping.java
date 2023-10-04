package ru.skypro.homework.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Commentary;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentaryMapping {

    public Comment commentEntityToCommentDto(Commentary commentary);
    public List<Comment> listFromCommentEntityToDto(List<Commentary> inputCommentsList);
    public Comments adCommentsToCommentsDTO(Ad ad);
    public Commentary createOrUpdateCommentDtoToCommentaryEntity(CreateOrUpdateComment createOrUpdateComment);
}
