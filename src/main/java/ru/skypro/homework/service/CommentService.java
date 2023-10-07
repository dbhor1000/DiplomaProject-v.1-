package ru.skypro.homework.service;


import ru.skypro.homework.dto.Comments;

public interface CommentService {

    public Comments getCommentsOfOneAd(Long AdId);

}
