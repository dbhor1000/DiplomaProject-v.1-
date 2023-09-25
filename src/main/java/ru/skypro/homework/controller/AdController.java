package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.AuthService;


@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AdController {

    //private final AdService adService;

    //
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "запрос выполнен, вывод информации об объявлениях;"
            )
    }
    )
    @GetMapping("/ads")
    public ResponseEntity<Ads> adInfo() {
            return ResponseEntity.ok().build();
    }

    //

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "запрос выполнен, вывод информации об объявлениях;"
            ),
            @ApiResponse(
                    responseCode = "401", description = "пользователь не авторизирован"
            )
    }
    )
    @PostMapping("/ads")
    public ResponseEntity<?> newAd(@RequestBody Ad ad) {
        return ResponseEntity.ok().build();
    }

    //

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "запрос выполнен, вывод информации об объявлениях;"
            ),
            @ApiResponse(
                    responseCode = "401", description = "отсутствует авторизация;"
            ),
            @ApiResponse(
                    responseCode = "404", description = "объявление не найдено;"
            )
    }
    )
    @GetMapping("/ads/{id}")
    public ResponseEntity<ExtendedAd> adInfoById(@PathVariable Integer id) {
        return ResponseEntity.ok().build();
    }

    //

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204", description = "запрос выполнен, нет возвращаемогосодержимого;"
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
    @DeleteMapping("/ads/{id}")
    public ResponseEntity<?> deleteAdById(@PathVariable Integer id) {
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
    @PatchMapping("/ads/{id}")
    public ResponseEntity<?> patchAdById(@PathVariable Integer id, @RequestBody CreateOrUpdateAd createOrUpdateAd) {
        return ResponseEntity.ok().build();
    }

    //

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "запрос выполнен;"
            ),
            @ApiResponse(
                    responseCode = "401", description = "отсутствует авторизация;"
            )
    }
    )
    @GetMapping("/ads/me")
    public ResponseEntity<?> showAuthorizedUserAd() {
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
    @PatchMapping("/ads/{id}/image")
    public ResponseEntity<?> patchAdPictureById(@PathVariable Integer id, @RequestBody String linkedPicture) {
        return ResponseEntity.ok().build();
    }











}
