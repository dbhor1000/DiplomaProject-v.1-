package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;

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
    private final ImageService imageService;

    public AdController(AdService adService, ImageService imageService) {
        this.adService = adService;
        this.imageService = imageService;
    }

    /**
     * Контроллер для получения всех объявлений.
     * @param authentication
     * @return все объявления как DTO объект Ads
     */

    @GetMapping
    public ResponseEntity<Ads> adInfo(Authentication authentication) {

        Ads ads = adService.allAdsPassToController();

        return ResponseEntity.ok(ads);
    }

    /**
     * Контроллер для добавления новых объявлений от лица авторизованного пользователя.
     * @param authentication, данные объявления в DTO объекте, MultipartFile с изображением
     * @return добавленное объявление DTO объект Ad
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<?> newAd(@RequestPart("properties") @Valid CreateOrUpdateAd createOrUpdateAd, @RequestPart("image") @Valid MultipartFile picture, Authentication authentication) {

        ru.skypro.homework.dto.Ad createdAdd = adService.newAd(createOrUpdateAd, picture, authentication.getName());

        if (createdAdd != null) {
            return ResponseEntity.ok(createdAdd);
        }

        return ResponseEntity.badRequest().build();
    }


    /**
     * Контроллер для получения информации об объявлении по id
     * @param id
     * @return найденное объявление как DTO объект ExtendedAd
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAd> adInfoById(@PathVariable Integer id) {

        ExtendedAd adById = adService.requestAdFromDatabaseById(id);
        return ResponseEntity.ok(adById);
    }

    /**
     * Контроллер для удаления объявления по id.
     * @param authentication, id
     * @return сообщение об успешном выполнении метода или ошибке
     */
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdById(@PathVariable int id, Authentication authentication) {

        if (adService.deleteAdById(id, authentication.getName())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


    /**
     * Контроллер для внесения изменений в объявление по id.
     * @param authentication
     * @return все объявления как DTO объект Ads
     */

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchAdById(@PathVariable Integer id, @RequestBody CreateOrUpdateAd createOrUpdateAd, Authentication authentication) {
        AdEntity ad = adService.editAdPatch(createOrUpdateAd, id, authentication.getName());
        if (ad == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    /**
     * Контроллер для получения объявлений авторизованного пользователя.
     * @param authentication
     * @return все объявления как DTO объект Ads
     */

    @GetMapping("/me")
    public ResponseEntity<?> showAuthorizedUserAd(Authentication authentication) {

        Ads retrievedAds = adService.authorizedUserAds(authentication.getName());

        return ResponseEntity.ok(retrievedAds);
    }

    /**
     * Контроллер для cмены изображения объявления, найденного по id.
     * @param authentication, id объявления, linkedPicture новое изображение
     * @return сообщение о выполнении метода
     */

    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> patchAdPictureById(@PathVariable Integer id, @RequestParam("image") MultipartFile linkedPicture, Authentication authentication) {
        adService.patchAdPictureById(linkedPicture, id, authentication.getName());
        return ResponseEntity.ok().build();
    }

    /**
     * Контроллер для получения изображения из базы данных. Служебный контроллер, необходимый для правильного функционирования front-end части.
     * @param id запрашиваемого из базы изображения
     * @return изображение в формате byte[]
     */

    @Transactional
    @GetMapping(value = "/{id}/adPicture", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/*"})
    public byte[] getImage(@PathVariable Integer id) throws IOException {

        //Метод получает картинку в формате byte[] из Entity Image, хранящегося в репозитории.
        //Изображения в Ad и UserEntity хранятся в качестве ссылок на объекты в репозитории Image.

        Image image = imageService.callImageById(id);
        return image.getImage();
    }













}
