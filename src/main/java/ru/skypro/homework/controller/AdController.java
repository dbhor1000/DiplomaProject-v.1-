package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.mapping.AdMapping;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;


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
    private final ImageRepository imageRepository;

    public AdController(AdService adService, AdMapping adMapping, UserRepository userRepository, ImageRepository imageRepository) {
        this.adService = adService;
        this.adMapping = adMapping;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    @GetMapping
    public ResponseEntity<Ads> adInfo(Authentication authentication) {

        Ads ads = adService.allAdsPassToController();

        return ResponseEntity.ok(ads);
    }

    //***
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //@RequestMapping(value = "", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @ResponseBody
    public ResponseEntity<?> newAd(@ModelAttribute("properties") @Valid CreateOrUpdateAd createOrUpdateAd, @RequestPart("image") @Valid MultipartFile picture, Authentication authentication) {

        ru.skypro.homework.dto.Ad createdAdd = adService.newAd(createOrUpdateAd, picture, "dmitry.hrshn@skyeng.ru");

        if (createdAdd != null) {
            return ResponseEntity.ok(createdAdd);
        }

        return ResponseEntity.badRequest().build();
    }


    //
    //Метод работает.
    //@PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> adInfoById(@PathVariable Integer id) {

        ExtendedAd adById = adService.requestAdFromDatabaseById(id);
        return ResponseEntity.ok(adById);
    }

    //
    //Метод работает.
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdById(@PathVariable int id, Authentication authentication) {

        if (adService.deleteAdById(id, authentication.getName())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    //
    //Метод работает

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> patchAdById(@PathVariable Integer id, @RequestBody CreateOrUpdateAd createOrUpdateAd, Authentication authentication) {
        AdEntity ad = adService.editAdPatch(createOrUpdateAd, id, authentication.getName());
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

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> patchAdPictureById(@PathVariable Integer id, @RequestParam("image") MultipartFile linkedPicture, Authentication authentication) {
        adService.patchAdPictureById(linkedPicture, id, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @Transactional
    @GetMapping(value = "/{id}/adPicture", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/*"})
    public byte[] getImage(@PathVariable Integer id) throws IOException {

        //Метод получает картинку в формате byte[] из Entity Image, хранящегося в репозитории.
        //Изображения в Ad и UserEntity хранятся в качестве ссылок на объекты в репозитории Image.

        Image image = imageRepository.getReferenceById(id);
        return image.getImage();
    }













}
