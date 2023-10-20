package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.UserEntity;

import java.util.List;

public interface AdService {

    //Функционал на фронтенде:
    //1. Получение всех объявлений (через репозиторий) - GET - DTO: Ads
    //2. Добавление объявлений - POST
    //3. Получение информации об объявлении по id - GET - DTO: ExtendedAd
    //4. Удаление объявления по id - DELETE
    //5. Обновление информации по id - PATCH - DTO: CreateOrUpdateAd
    //6. Получение объявлений авторизованного пользователя - GET - DTO: Ads
    //7. Обновление картинки объявления - PATCH

    public List<Ad> getAllAdsFromDatabase();
    public Ads allAdsPassToController();
    public Ad newAd(CreateOrUpdateAd createOrUpdateAd, MultipartFile image, String username);
    public ExtendedAd requestAdFromDatabaseById(int id);
    public AdEntity editAdPatch(CreateOrUpdateAd createOrUpdateAd, int id, String username);
    public Ads authorizedUserAds(String username);
    public boolean patchAdPictureById(MultipartFile image, int adId, String username);
    public boolean deleteAdById(int id, String username);







}
