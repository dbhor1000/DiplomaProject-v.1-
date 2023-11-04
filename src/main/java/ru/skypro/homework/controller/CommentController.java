package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;

import javax.transaction.Transactional;


@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class CommentController {

    //Функционал на фронтенде:
    //1. Получение всех комментариев объявления (через репозиторий) - GET - DTO: Comments
    //2. Добавление комментария к объявление - POST - DTO: CreateOrUpdateComment
    //3. Удаление комментария по id - DELETE
    //4. Обновление комментария по id (+id объявления) - PATCH - DTO: CreateOrUpdateComment

    private final CommentService commentService;
    private final AdService adService;

    public CommentController(CommentService commentService, AdService adService) {
        this.commentService = commentService;
        this.adService = adService;
    }

    /**
     * Контроллер для получения комментариев, относящихся к определённому объявлению, по id объявления.
     * @param id объявления
     * @return DTO объект Comments, представляющий собой список комментариев.
     */

    @GetMapping("/{id}/comments")
    public ResponseEntity<Comments> getAdComments(@PathVariable int id) {

        if (commentService.getCommentsOfOneAd(id) == null) {
            return ResponseEntity.notFound().build();
        } else {
            Comments retrievedComments = commentService.getCommentsOfOneAd(id);
            return ResponseEntity.ok(retrievedComments);
        }
    }

    /**
     * Контроллер для добавления комментария к объявлению.
     * @param id объявления, DTO объект CreateOrUpdateComment с данными комментария.
     * @return добавленный комментарий или сообщение об ошибке.
     */

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> addCommentToAd(@PathVariable Integer id, @RequestBody CreateOrUpdateComment createOrUpdateComment) {
        if (adService.callAdById(id) == null) {
            return ResponseEntity.notFound().build();
        } else {
            Comment addedComment = commentService.addCommentToAd(createOrUpdateComment, id);
            return ResponseEntity.ok(addedComment);
        }
    }

    /**
     * Контроллер для удаления комментария по id.
     * @param authentication, id объялвения, id объявления, к которому относится комментарий
     * @return сообщение об успехе или ошибке
     */

    @Transactional
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteCommentById(@PathVariable Integer adId, @PathVariable Integer commentId, Authentication authentication) {


        boolean deletedYesNo = commentService.deleteCommentByIdAndAdId(adId, commentId, authentication.getName());
        if (deletedYesNo) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }

    /**
     * Контроллер для редактирования комментария по id.
     * @param authentication, id объявления, id комментария, DTO объект с новыми данными объявления
     * @return сообщение об успехе или ошибке
     */
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> patchCommentById(@PathVariable("adId") Integer adId, @PathVariable("commentId") Integer commentId, @RequestBody CreateOrUpdateComment createOrUpdateComment, Authentication authentication) {
        if (commentService.patchCommentByIdAndAdId(adId, commentId, createOrUpdateComment, authentication.getName())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
