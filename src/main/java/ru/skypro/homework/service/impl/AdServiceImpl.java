package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
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
    public ru.skypro.homework.dto.Ad newAd(ru.skypro.homework.dto.Ad ad, String image) {
        Ad mappedDTO = adMapping.adDtoToAdEntity(ad);
        mappedDTO.setImage(image);
        adRepository.save(mappedDTO);
        return ad;
    }

    @Override
    public ExtendedAd requestAdFromDatabaseById(Long id){
        ExtendedAd adFoundAndMapped = adMapping.adEntityToExtendedAdDto(adRepository.getReferenceById(id));
        return adFoundAndMapped;
    }

    @Override
    public boolean deleteAdById(Long id) {

        if (adRepository.findById(id).isPresent()) {
            adRepository.deleteById(id);
            //List<Commentary> commentsToDelete = commentaryRepository.findAllByAdRelated(adRepository.findById(id));
            //commentaryRepository.deleteByAd(adRepository.findById(id));

            return true;
        } else {
            throw new AdNotFoundException();
        }
    }

    @Override
    public Ad editAdPatch(CreateOrUpdateAd createOrUpdateAd, Long id) {
        if (adRepository.findById(id).isPresent()) {
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
