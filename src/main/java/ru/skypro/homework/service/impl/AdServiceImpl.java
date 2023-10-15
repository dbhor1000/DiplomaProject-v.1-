package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Commentary;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentaryRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.mapping.AdMapping;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


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

    public AdServiceImpl(UserRepository userRepository, AdRepository adRepository, AdMapping adMapping) {
        this.userRepository = userRepository;
        this.adRepository = adRepository;
        this.adMapping = adMapping;
    }

    @Override
    public List<ru.skypro.homework.dto.Ad> getAllAdsFromDatabase() {
        List<Ad> allAdsFromDatabaseCollect = adRepository.findAll();
        List<ru.skypro.homework.dto.Ad> allAdsInDTO = adMapping.AdEntityListToAdsDto(allAdsFromDatabaseCollect);
        return allAdsInDTO;
    }

    @Override
    public Ads allAdsPassToController() {

        Ads ads = new Ads();
        ads.setResults(getAllAdsFromDatabase());
        ads.setCount(getAllAdsFromDatabase().size());
        return ads;
    }

    @Override
    public ru.skypro.homework.dto.Ad newAd(CreateOrUpdateAd createOrUpdateAd, MultipartFile image, String username) {

        Ad mappedDTO = adMapping.createOrUpdateAdDtoToAdEntity(createOrUpdateAd);
        mappedDTO.setUserRelated(userRepository.findByUsername(username));

        try {
            byte[] imageToBytes = image.getBytes();
            mappedDTO.setImage(imageToBytes);//Сохраняем изображение как строку, получившуюся из массива байтов при конвертации. Далее, можно конвертировать обратно.

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        adRepository.save(mappedDTO);
        ru.skypro.homework.dto.Ad adDTOForOutput = adMapping.adEntityToAdDto(mappedDTO);
        return adDTOForOutput;

    }

    @Override
    public ExtendedAd requestAdFromDatabaseById(int id){
        ExtendedAd adFoundAndMapped = adMapping.adEntityToExtendedAdDto(adRepository.getReferenceById(id));
        return adFoundAndMapped;
    }

    @Override
    public boolean deleteAdById(int id) {

        if (adRepository.findById(id) != null) {
            adRepository.deleteById(id);


            return true;
        } else {
            throw new AdNotFoundException();
        }
    }

    @Override
    public Ad editAdPatch(CreateOrUpdateAd createOrUpdateAd, int id) {
        if (adRepository.findById(id) != null) {
            Ad adFoundToPatch = adRepository.getReferenceById(id);
            adFoundToPatch.setTitle(createOrUpdateAd.getTitle());
            adFoundToPatch.setPrice(createOrUpdateAd.getPrice());
            adFoundToPatch.setDescription(createOrUpdateAd.getDescription());
            adRepository.save(adFoundToPatch);
            return adFoundToPatch;
        } else {
            throw new AdNotFoundException();
        }
    }

    @Override
    public Ads authorizedUserAds(String username) {

        UserEntity authorizedUser = userRepository.findByUsername(username);
        Ads authorizedUserAds = adMapping.userAdsToAdsDTO(authorizedUser);
        return authorizedUserAds;
    }

}
