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
public class CommentController {

    //private final CommentService commentService

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "запрос выполнен;"
            ),
            @ApiResponse(
                    responseCode = "401", description = "отсутствует авторизация;"
            ),
            @ApiResponse(
                    responseCode = "404", description = "объявление не найдено;"
            )
    }
    )
    @GetMapping("/ads/{id}/comments")
    public ResponseEntity<Comments> getAdComments(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

    //

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "запрос выполнен;"
            ),
            @ApiResponse(
                    responseCode = "401", description = "отсутствует авторизация;"
            ),
            @ApiResponse(
                    responseCode = "403", description = "операция запрещена;"
            ),
            @ApiResponse(
                    responseCode = "404", description = "объявление не найдено;"
            )
    }
    )
    @PostMapping("/ads/{id}/comments")
    public ResponseEntity<?> deleteAdById(@PathVariable Integer id, @RequestBody CreateOrUpdateComment createOrUpdateComment) {
        return ResponseEntity.ok().build();
    }

    //

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "запрос выполнен;"
            ),
            @ApiResponse(
                    responseCode = "401", description = "отсутствует авторизация;"
            ),
            @ApiResponse(
                    responseCode = "403", description = "операция запрещена;"
            ),
            @ApiResponse(
                    responseCode = "404", description = "объявление не найдено;"
            )
    }
    )
    @DeleteMapping("/ads/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteCommentById(@RequestBody Integer adId, Integer commentId) {
        return ResponseEntity.ok().build();
    }

    //

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "запрос выполнен;"
            ),
            @ApiResponse(
                    responseCode = "401", description = "отсутствует авторизация;"
            ),
            @ApiResponse(
                    responseCode = "403", description = "операция запрещена;"
            ),
            @ApiResponse(
                    responseCode = "404", description = "объявление не найдено;"
            )
    }
    )
    @PatchMapping("/ads/{adId}/comments/{commentId}")
    public ResponseEntity<?> patchCommentById(@RequestBody Integer adId, Integer commentId, @RequestBody CreateOrUpdateComment createOrUpdateComment) {
        return ResponseEntity.ok().build();
    }

}
