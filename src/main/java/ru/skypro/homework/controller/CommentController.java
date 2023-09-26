package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;


@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class CommentController {

    //private final CommentService commentService

    @GetMapping("/{id}/comments")
    public ResponseEntity<Comments> getAdComments(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

    //

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> deleteAdById(@PathVariable Integer id, @RequestBody CreateOrUpdateComment createOrUpdateComment) {
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
