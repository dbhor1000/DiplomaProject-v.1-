package ru.skypro.homework.service;

import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
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

    //***
    public ru.skypro.homework.dto.Ad newAd(ru.skypro.homework.dto.Ad ad, String image);

    public ExtendedAd requestAdFromDatabaseById(Long id);

    public boolean deleteAdById(Long id);
    public ru.skypro.homework.model.Ad editAdPatch(CreateOrUpdateAd createOrUpdateAd, Long id);
    public Ads authorizedUserAds(String username);







}
