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

    @Override
    public List<Ad> getAllAdsFromDatabase() {
        List<AdEntity> allAdsFromDatabaseCollect = adRepository.findAll();
        List<Ad> allAdsInDTO = adMapping.AdEntityListToAdsDto(allAdsFromDatabaseCollect);
        return allAdsInDTO;
    }

    @Override
    public Ads allAdsPassToController() {

        Ads ads = new Ads();
        ads.setResults(getAllAdsFromDatabase());
        ads.setCount(getAllAdsFromDatabase().size());
        return ads;
    }

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

    //@Override
    //public ru.skypro.homework.dto.Ad newAd(CreateOrUpdateAd createOrUpdateAd, MultipartFile image, String username) {
    //
    //    Ad mappedDTO = adMapping.createOrUpdateAdDtoToAdEntity(createOrUpdateAd);
    //    mappedDTO.setUserRelated(userRepository.findByUsername(username));
    //
    //    try {
    //
    //        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
    //        byte[] imageToBytes = image.getBytes();
    //        Path write = Files.write(Paths.get(UUID.randomUUID() + "." + extension), imageToBytes);
    //        mappedDTO.setImage(write.toString());//Сохраняем изображение как строку, получившуюся из массива байтов при конвертации. Далее, можно конвертировать обратно.
    //
    //    } catch (IOException e) {
    //        throw new RuntimeException(e);
    //    }
    //
    //    adRepository.save(mappedDTO);
    //    ru.skypro.homework.dto.Ad adDTOForOutput = adMapping.adEntityToAdDto(mappedDTO);
    //    return adDTOForOutput;
    //
    //}


    //@Override
    //public ru.skypro.homework.dto.Ad newAd(ru.skypro.homework.dto.Ad ad, MultipartFile image, String username) {
    //
    //    try {
    //
    //        Ad mappedDTO = adMapping.adDtoToAdEntity(ad);
    //        mappedDTO.setUserRelated(userRepository.findByUsername(username));
    //        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
    //        byte[] imageToBytes = image.getBytes();
    //        Path write = Files.write(Paths.get(UUID.randomUUID() + "." + extension), imageToBytes);
    //        mappedDTO.setImage(write.toString());//Сохраняем изображение как строку, получившуюся из массива байтов при конвертации. Далее, можно конвертировать обратно.
    //        adRepository.saveAndFlush(mappedDTO);
    //        ru.skypro.homework.dto.Ad mappedOutput = adMapping.adEntityToAdDto(mappedDTO);
    //        return mappedOutput;
    //
    //    } catch (IOException e) {
    //        throw new RuntimeException(e);
    //    }
    //}

    @Override
    public ExtendedAd requestAdFromDatabaseById(int id){
        ExtendedAd adFoundAndMapped = adMapping.adEntityToExtendedAdDto(adRepository.getReferenceById(id));
        return adFoundAndMapped;
    }

    @Transactional
    @Override
    public boolean deleteAdById(int id, String username) {

        AdEntity adFromRepository = adRepository.findById(id);
        UserEntity userWhoPostedAd = adRepository.findById(id).getUserRelated();
        UserEntity authorizedUser = userRepository.findByUsername(username);
        Role authorizedUserRole = authorizedUser.getRole();

        if ((Optional.of(adFromRepository).isPresent() && authorizedUserRole == Role.USER && userWhoPostedAd == authorizedUser) || (Optional.of(adFromRepository).isPresent() && authorizedUserRole == Role.ADMIN)) {
            adRepository.deleteById(id);
            adRepository.flush();
            return true;
        } else {
            throw new AdNotFoundException();
        }
    }

    @Override
    public AdEntity editAdPatch(CreateOrUpdateAd createOrUpdateAd, int id, String username) {

        AdEntity adFoundToPatch = adRepository.findById(id);
        UserEntity userWhoPostedAd = adRepository.findById(id).getUserRelated();
        UserEntity authorizedUser = userRepository.findByUsername(username);
        Role authorizedUserRole = authorizedUser.getRole();

        if ((Optional.of(adFoundToPatch).isPresent() && authorizedUserRole == Role.USER && userWhoPostedAd == authorizedUser) || (Optional.of(adFoundToPatch).isPresent() && authorizedUserRole == Role.ADMIN)) {

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

    @Override
    public AdEntity callAdById (int id) {
        AdEntity adEntity = adRepository.getReferenceById(id);
        return adEntity;
    }
}
