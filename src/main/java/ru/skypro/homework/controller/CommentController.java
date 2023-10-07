package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentService;


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

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<Comments> getAdComments(@PathVariable Integer id) {

        if (commentService.getCommentsOfOneAd(id.longValue()) == null) {
            return ResponseEntity.notFound().build();
        } else {
            Comments retrievedComments = commentService.getCommentsOfOneAd(id.longValue());
            return ResponseEntity.ok(retrievedComments);
        }
    }

    //

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> addCommentToAd(@PathVariable Integer id, @RequestBody CreateOrUpdateComment createOrUpdateComment) {
        return ResponseEntity.ok().build();
    }

    //

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteCommentById(@PathVariable Integer adId, Integer commentId) {
        return ResponseEntity.ok().build();
    }

    //

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> patchCommentById(@PathVariable Integer adId, Integer commentId, @RequestBody CreateOrUpdateComment createOrUpdateComment) {
        return ResponseEntity.ok().build();
    }

}
