package ru.skypro.homework.service;


import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

public interface CommentService {

    public Comments getCommentsOfOneAd(int AdId);
    public Comment addCommentToAd(CreateOrUpdateComment commentToAdd, int adId);
    public boolean patchCommentByIdAndAdId(int adId, Integer commentId, CreateOrUpdateComment createOrUpdateComment, String username);
    public boolean deleteCommentByIdAndAdId(int adId, Integer commentId, String username);
}
