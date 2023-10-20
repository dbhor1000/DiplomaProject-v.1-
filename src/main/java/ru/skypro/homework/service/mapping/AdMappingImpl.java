package ru.skypro.homework.service.mapping;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;

import java.util.*;

@Component
public class AdMappingImpl implements AdMapping {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public AdMappingImpl(UserRepository userRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    //Этот маппер не нужен(/сломался/требует внимания/выглядит грустным/(был) слишком хорош :P)?
    @Override
    public Ad adEntityToAdDto(AdEntity ad) {
        if (ad == null) {
            return null;
        }
        Ad assembledAdDto = new Ad();
        assembledAdDto.setAuthor(ad.getUserRelated().getId());
        assembledAdDto.setPk(ad.getId());
        assembledAdDto.setImage("/ads/" + ad.getImageAd().getId() + "/adPicture");
        assembledAdDto.setPrice(ad.getPrice());
        assembledAdDto.setTitle(ad.getTitle());
        return assembledAdDto;
    }
    @Override
    public ExtendedAd adEntityToExtendedAdDto(AdEntity ad) {
        if (ad == null) {
            return null;
        }
        ExtendedAd assembledExtendedAdDto = new ExtendedAd();
        assembledExtendedAdDto.setPk(ad.getId());
        assembledExtendedAdDto.setAuthorFirstName(ad.getUserRelated().getFirstName());
        assembledExtendedAdDto.setAuthorLastName(ad.getUserRelated().getLastName());
        assembledExtendedAdDto.setDescription(ad.getDescription());
        assembledExtendedAdDto.setEmail(ad.getUserRelated().getUsername());
        assembledExtendedAdDto.setImage("/ads/" + ad.getImageAd().getId() + "/adPicture");
        assembledExtendedAdDto.setPhone(ad.getUserRelated().getPhone());
        assembledExtendedAdDto.setPrice(ad.getPrice());
        assembledExtendedAdDto.setTitle(ad.getTitle());
        return assembledExtendedAdDto;

    }

    //Метод не задействован.

    //@Override
    //public ru.skypro.homework.model.Ad adDtoToAdEntity(Ad ad){
    //    if (ad == null) {
    //        return null;
    //    }
    //
    //    List<Commentary> comments = new ArrayList<>();
    //    ru.skypro.homework.model.Ad modifiedAdEntity = new ru.skypro.homework.model.Ad();
    //    modifiedAdEntity.setComments(comments);
    //    modifiedAdEntity.setUserRelated(userRepository.getReferenceById((ad.getAuthor().longValue())));
    //    //modifiedAdEntity.setId(ad.getPk().longValue()); //Сеттер для поля Long принимает Integer? ? ?
    //    modifiedAdEntity.setImage(null);
    //    modifiedAdEntity.setPrice(ad.getPrice());
    //    modifiedAdEntity.setTitle(ad.getTitle());
    //    return modifiedAdEntity;
    //
    //}

    @Override
    public AdEntity createOrUpdateAdDtoToAdEntity (CreateOrUpdateAd createOrUpdateAd){
        if (createOrUpdateAd == null) {
            return null;
        }

        AdEntity modifiedAdEntity = new AdEntity();
        modifiedAdEntity.setPrice(createOrUpdateAd.getPrice());
        modifiedAdEntity.setTitle(createOrUpdateAd.getTitle());
        modifiedAdEntity.setDescription(createOrUpdateAd.getDescription());
        return modifiedAdEntity;

    }

    @Override
    public List<Ad> AdEntityListToAdsDto(List<AdEntity> inputAdList) {
        if (inputAdList == null) {
            return null;
        }
        List<Ad> mappedList = new ArrayList<Ad>(inputAdList.size());
        for (AdEntity ad : inputAdList) {
            mappedList.add(adEntityToAdDto(ad));
        }
        return mappedList;
    }

    @Override
    public Ads userAdsToAdsDTO(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        Ads ads = new Ads();
        ads.setResults(AdEntityListToAdsDto(userEntity.getAds()));
        ads.setCount(userEntity.getAds().size());
        return ads;
    }
}
