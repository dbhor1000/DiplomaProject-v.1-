package ru.skypro.homework.service.mapping;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.Commentary;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentaryMappingImpl implements CommentaryMapping {

    @Override
    public Comment commentEntityToCommentDto(Commentary commentary) {
        if (commentary == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setAuthor(commentary.getUserRelated().getId());
        if(commentary.getUserRelated().getImageAvatar() != null) {
            comment.setAuthorImage("/users/" + commentary.getUserRelated().getImageAvatar().getId() + "/avatar");
        } else {
            comment.setAuthorImage(null);
        }
        comment.setAuthorFirstName(commentary.getUserRelated().getFirstName());
        comment.setCreatedAt(commentary.getCreatedAt());
        comment.setPk(commentary.getId());
        comment.setText(commentary.getText());
        return comment;

    }

    @Override
    public List<Comment> listFromCommentEntityToDto(List<Commentary> inputCommentsList) {
        if (inputCommentsList == null) {
            return null;
        }
        List<Comment> mappedList = new ArrayList<Comment>(inputCommentsList.size());
        for (Commentary commentary : inputCommentsList) {
            mappedList.add(commentEntityToCommentDto(commentary));
        }
        return mappedList;

    }

    @Override
    public Comments adCommentsToCommentsDTO(AdEntity ad) {
        if (ad == null) {
            return null;
        }

        Comments comments = new Comments();
        comments.setResults(listFromCommentEntityToDto(ad.getComments()));
        comments.setCount(ad.getComments().size());
        return comments;
    }

    //--- --- ---
    @Override
    public Commentary createOrUpdateCommentDtoToCommentaryEntity(CreateOrUpdateComment createOrUpdateComment) {
    if (createOrUpdateComment == null) {
        return null;
    }

    Commentary commentary = new Commentary();
    commentary.setText(createOrUpdateComment.getText());
    return commentary;
}
}
