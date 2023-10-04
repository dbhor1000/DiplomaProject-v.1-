package ru.skypro.homework.service.mapping;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Commentary;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentaryMappingImpl implements CommentaryMapping {

    //Судя по шаблону приложения, только 2 метода в разделе "Комментарии" задействуют DTO "CreateOrUpdateComment" (Front -> БД)
    //Остальные DTO не задействованы на данный момент?
    //---> Метод GET задействует DTO Comments, которое задействует DTO Comment.

    @Override
    public Comment commentEntityToCommentDto(Commentary commentary) {
        if (commentary == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setAuthor(commentary.getUserRelated().getId());
        comment.setAuthorImage(commentary.getUserRelated().getImage());
        comment.setAuthorFirstName(commentary.getUserRelated().getFirstName());
        comment.setCreatedAt(commentary.getCreatedAt());
        comment.setPk(Math.toIntExact(commentary.getId()));
        comment.setText(commentary.getText());
        return comment;

    }

    //Судя по всему, маппер не требуется. Пока что под комментарием, на всякий случай.
    //public Commentary CommentDtoToCommentEntity(Comment comment) {
    //    if (comment == null) {
    //        return null;
    //    }
    //    Commentary commentary  = new Commentary();
    //    return commentary;
    //}

    //--- --- ---
    //DTO Comments включает список List<Comment> results. Этот список можно получить из объявления в БД (сущность включает поле
    //List<Commentary> comments. Нужно переконвертировать объявления из этого списка Commentary(Entity) -> Comment(DTO)
    //и далее получить список List<Comment> commentsDTO.


    //Метод для конвертации списка объявлений, взятого из БД Ad.
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
    public Comments adCommentsToCommentsDTO(Ad ad) {
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
