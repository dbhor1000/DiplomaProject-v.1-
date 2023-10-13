package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.mapping.AdMapping;


@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdController {

    //Функционал на фронтенде:
    //1. Получение всех объявлений (через репозиторий) - GET - DTO: Ads - +++
    //2. Добавление объявлений - POST - ???
    //3. Получение информации об объявлении по id - GET - DTO: ExtendedAd - +++
    //4. Удаление объявления по id - DELETE - +++
    //5. Обновление информации по id - PATCH - DTO: CreateOrUpdateAd - +++
    //6. Получение объявлений авторизованного пользователя - GET - DTO: Ads - +++
    //7. Обновление картинки объявления - PATCH - Позже

    private final AdService adService;
    private final AdMapping adMapping;
    private final UserRepository userRepository;

    public AdController(AdService adService, AdMapping adMapping, UserRepository userRepository) {
        this.adService = adService;
        this.adMapping = adMapping;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<Ads> adInfo(Authentication authentication) {

        Ads ads = adService.allAdsPassToController();

        return ResponseEntity.ok(ads);
    }

    //
    //***

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> newAd(@RequestParam("properties") Ad ad, @RequestParam("image") MultipartFile picture) {
        adService.newAd(ad, String.valueOf(picture));
        return ResponseEntity.ok().build();
    }

    //
    //Метод работает.
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> adInfoById(@PathVariable Integer id) {

        ExtendedAd adById = adService.requestAdFromDatabaseById(id.longValue());
        return ResponseEntity.ok(adById);
    }

    //
    //Метод работает.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdById(@PathVariable Integer id) {

        if (adService.deleteAdById(id.longValue())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    //
    //Метод работает

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchAdById(@PathVariable Integer id, @RequestBody CreateOrUpdateAd createOrUpdateAd) {
        ru.skypro.homework.model.Ad ad = adService.editAdPatch(createOrUpdateAd, id.longValue());
        if (ad == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    //
    //Метод работает.

    @GetMapping("/me")
    public ResponseEntity<?> showAuthorizedUserAd(Authentication authentication) {

        Ads retrievedAds = adService.authorizedUserAds(authentication.getName());

        return ResponseEntity.ok(retrievedAds);
    }

    //
    //Работа с изображениями на следующих этапах разработки.

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> patchAdPictureById(@PathVariable Integer id, @RequestParam("image") MultipartFile linkedPicture) {
        return ResponseEntity.ok().build();
    }











}
