package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.mapping.AdMapping;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class AdServiceImpl implements AdService {

    //Функционал на фронтенде:
    //1. Получение всех объявлений (через репозиторий) - GET - DTO: Ads
    //2. Добавление объявлений - POST - DTO: Ad
    //3. Получение информации об объявлении по id - GET - DTO: ExtendedAd
    //4. Удаление объявления по id - DELETE
    //5. Обновление информации по id - PATCH - DTO: CreateOrUpdateAd
    //6. Получение объявлений авторизованного пользователя - GET - DTO: Ads
    //7. Обновление картинки объявления - PATCH

    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final AdMapping adMapping;
    private final ImageRepository imageRepository;

    public AdServiceImpl(UserRepository userRepository, AdRepository adRepository, AdMapping adMapping, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.adRepository = adRepository;
        this.adMapping = adMapping;
        this.imageRepository = imageRepository;
    }

    /**
     * Сервисный метод для получения всех объявлений из репозитория (список) и конвертации в список DTO-объектов.
     * @return список DTO-objects List<Ad>
     */

    @Override
    public List<Ad> getAllAdsFromDatabase() {
        List<AdEntity> allAdsFromDatabaseCollect = adRepository.findAll();
        List<Ad> allAdsInDTO = adMapping.AdEntityListToAdsDto(allAdsFromDatabaseCollect);
        return allAdsInDTO;
    }

    /**
     * Сервисный метод для получения DTO-object Ads из списка List<Ad> и количества объектов в этом списке.
     * @return DTO-object Ads
     */

    @Override
    public Ads allAdsPassToController() {

        Ads ads = new Ads();
        ads.setResults(getAllAdsFromDatabase());
        ads.setCount(getAllAdsFromDatabase().size());
        return ads;
    }

    /**
     * Сервисный метод для добавления объявления.
     * @param createOrUpdateAd DTO-object с параметрами объявления, MultiPart файл с изображением и username авторизованного пользователя
     * @return DTO-object Ad
     */

    @Transactional
    @Override
    public Ad newAd(CreateOrUpdateAd createOrUpdateAd, MultipartFile image, String username) {

        AdEntity mappedDTO = adMapping.createOrUpdateAdDtoToAdEntity(createOrUpdateAd);
        mappedDTO.setUserRelated(userRepository.findByUsername(username));

        try {

            byte[] imageToBytes = image.getBytes();
            Image multipartToEntity = new Image();
            multipartToEntity.setImage(imageToBytes);
            imageRepository.save(multipartToEntity);
            mappedDTO.setImageAd(multipartToEntity);//Сохраняем изображение как строку, получившуюся из массива байтов при конвертации. Далее, можно конвертировать обратно.
            adRepository.saveAndFlush(mappedDTO);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //Ad adDTOForOutput = adMapping.adEntityToAdDto(mappedDTO);
        Ad adDTOForOutput = adMapping.adEntityToAdDto(mappedDTO);
        return adDTOForOutput;

    }

    /**
     * Сервисный метод для запроса объявления из БД по id.
     * @param id объвления
     * @return DTO-object ExtendedAd
     */

    @Override
    public ExtendedAd requestAdFromDatabaseById(int id){
        ExtendedAd adFoundAndMapped = adMapping.adEntityToExtendedAdDto(adRepository.getReferenceById(id));
        return adFoundAndMapped;
    }

    /**
     * Сервисный метод для удаления объявления по id.
     * @param id объявления и username авторизованного пользователя
     * @return boolean true/false
     */

    @Transactional
    @Override
    public boolean deleteAdById(int id, String username) {

        AdEntity adFromRepository = adRepository.findById(id);
        UserEntity userWhoPostedAd = adRepository.findById(id).getUserRelated();
        UserEntity authorizedUser = userRepository.findByUsername(username);
        Role authorizedUserRole = authorizedUser.getRole();

        //if ((Optional.of(adFromRepository).isPresent() && authorizedUserRole == Role.USER && userWhoPostedAd == authorizedUser) || (Optional.of(adFromRepository).isPresent() && authorizedUserRole == Role.ADMIN)) {
        // if ((adFromRepository != null && (userWhoPostedAd.equals(authorizedUser))) || (authorizedUserRole == Role.ADMIN)) {
        //if (adFromRepository != null && ((userWhoPostedAd.equals(authorizedUser) || authorizedUserRole == Role.ADMIN))) {
        if ((userWhoPostedAd.equals(authorizedUser) || authorizedUserRole == Role.ADMIN)) {
            adRepository.deleteById(id);
            adRepository.flush();
            return true;
        } else {
            throw new AdNotFoundException();
        }
    }

    /**
     * Сервисный метод для редактирования объявления по id.
     * @param createOrUpdateAd DTO-object с новыми параметрами объявления, id объявления и username авторизованного пользователя
     * @return Entity-object AdEntity
     */

    @Override
    public AdEntity editAdPatch(CreateOrUpdateAd createOrUpdateAd, int id, String username) {

        AdEntity adFoundToPatch = adRepository.findById(id);
        UserEntity userWhoPostedAd = adRepository.findById(id).getUserRelated();
        UserEntity authorizedUser = userRepository.findByUsername(username);
        Role authorizedUserRole = authorizedUser.getRole();

        //if ((Optional.of(adFoundToPatch).isPresent() && authorizedUserRole == Role.USER && userWhoPostedAd == authorizedUser) || (Optional.of(adFoundToPatch).isPresent() && authorizedUserRole == Role.ADMIN)) {
        //if (adFoundToPatch != null && ((userWhoPostedAd.equals(authorizedUser) || authorizedUserRole == Role.ADMIN))) {
        if ((userWhoPostedAd.equals(authorizedUser) || authorizedUserRole == Role.ADMIN)) {
            adFoundToPatch.setTitle(createOrUpdateAd.getTitle());
            adFoundToPatch.setPrice(createOrUpdateAd.getPrice());
            adFoundToPatch.setDescription(createOrUpdateAd.getDescription());
            adRepository.save(adFoundToPatch);
            return adFoundToPatch;
        } else {
            throw new AdNotFoundException();
        }
    }

    /**
     * Сервисный метод для получения объявлений авторизованного пользователя.
     * @param username авторизованного пользователя
     * @return DTO-object Ads
     */

    @Override
    public Ads authorizedUserAds(String username) {

        UserEntity authorizedUser = userRepository.findByUsername(username);
        Ads authorizedUserAds = adMapping.userAdsToAdsDTO(authorizedUser);
        return authorizedUserAds;
    }

    /**
     * Сервисный метод для редактирования изображения объявления по id.
     * @param image MultiPart файл с изображением, id редактируемого объявления, username авторизованного пользователя
     * @return boolean true/false
     */

    @Override
    public boolean patchAdPictureById(MultipartFile image, int adId, String username) {

        AdEntity adToModify = adRepository.findById(adId);
        UserEntity userWhoPostedAd = adRepository.findById(adId).getUserRelated();
        UserEntity authorizedUser = userRepository.findByUsername(username);
        Role authorizedUserRole = authorizedUser.getRole();

        Image multipartToImage = new Image();

        try {

            byte[] imageToBytes = image.getBytes();
            multipartToImage.setImage(imageToBytes);
            imageRepository.save(multipartToImage);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if ((Optional.of(adToModify).isPresent() && authorizedUserRole == Role.USER && userWhoPostedAd == authorizedUser) || (Optional.of(adToModify).isPresent() && authorizedUserRole == Role.ADMIN)) {

                adToModify.setImageAd(multipartToImage);//Сохраняем изображение как строку, получившуюся из массива байтов при конвертации. Далее, можно конвертировать обратно.
                adRepository.save(adToModify);
                return true;
        }

        return false;
    }

    /**
     * Сервисный метод для выхова объявления по id.
     * @param id вызываемого объявления
     * @return Entity-object AdEntity
     */

    @Override
    public AdEntity callAdById (int id) {
        AdEntity adEntity = adRepository.getReferenceById(id);
        return adEntity;
    }
}
