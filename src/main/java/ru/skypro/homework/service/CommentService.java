package ru.skypro.homework.service;


import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

public interface CommentService {

    public Comments getCommentsOfOneAd(Long AdId);
    public Comment addCommentToAd(CreateOrUpdateComment commentToAdd, Long adId);
    public boolean deleteCommentByIdAndAdId(Integer adId, Integer commentId);
    public boolean patchCommentByIdAndAdId(Integer adId, Integer commentId, CreateOrUpdateComment createOrUpdateComment);
}
