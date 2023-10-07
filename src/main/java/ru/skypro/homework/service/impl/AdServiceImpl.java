package ru.skypro.homework.service.impl;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.repository.AdRepository;
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

    private final AdRepository adRepository;
    private final AdMapping adMapping;

    public AdServiceImpl(AdRepository adRepository, AdMapping adMapping) {
        this.adRepository = adRepository;
        this.adMapping = adMapping;
    }

    //1.---Ниже 2 взаимосвязанных метода---Методы получают все объявления из репозитория, конвертируют список в формат DTO и далее
    //собирается DTO Ads, готовый к использованию в контроллере.

    //Задействован маппер List<Ad(Entity)> -> List<Ad(DTO)>
    @Override
    public List<ru.skypro.homework.dto.Ad> getAllAdsFromDatabase() {
        List<Ad> allAdsFromDatabaseCollect = adRepository.findAll();
        List<ru.skypro.homework.dto.Ad> allAdsInDTO = adMapping.AdEntityListToAdsDto(allAdsFromDatabaseCollect);
        return allAdsInDTO;
    }

    //С методом ниже не до конца ясно. Похож на маппер, но без входных параметров. Пока что в сервисе. Работает.
    @Override
    public Ads allAdsPassToController() {
        Ads ads = new Ads();
        ads.setResults(getAllAdsFromDatabase());
        ads.setCount(getAllAdsFromDatabase().size());
        return ads;
    }
    //--- --- ---1.


    //2.---Методы для добавления объявлений
    //06.10 Не удалось настроить. Возможно, дело в маппинге или чём-то другом.

    //Этот метод сервиса позволяет сохранить объявление. ***
    @Override
    public ru.skypro.homework.dto.Ad newAd(ru.skypro.homework.dto.Ad ad) {
        adRepository.save(adMapping.adDtoToAdEntity(ad));
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

}
