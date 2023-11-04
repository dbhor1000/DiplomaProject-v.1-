package ru.skypro.homework.service.mapping;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdMappingImpl implements AdMapping {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public AdMappingImpl(UserRepository userRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public Ad adEntityToAdDto(AdEntity ad) {
        if (ad == null) {
            return null;
        }
        Ad assembledAdDto = new Ad();
        assembledAdDto.setAuthor(ad.getUserRelated().getId());
        assembledAdDto.setPk(ad.getId());
        if(ad.getImageAd() != null) {
            assembledAdDto.setImage("/ads/" + ad.getImageAd().getId() + "/adPicture");
        } else {
            assembledAdDto.setImage(null);
        }
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
        if(ad.getImageAd() != null) {
            assembledExtendedAdDto.setImage("/ads/" + ad.getImageAd().getId() + "/adPicture");
        } else {
            assembledExtendedAdDto.setImage(null);
        }
        assembledExtendedAdDto.setPhone(ad.getUserRelated().getPhone());
        assembledExtendedAdDto.setPrice(ad.getPrice());
        assembledExtendedAdDto.setTitle(ad.getTitle());
        return assembledExtendedAdDto;

    }

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
    public List<Ad> adEntityListToAdsDto(List<AdEntity> inputAdList) {
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
        ads.setResults(adEntityListToAdsDto(userEntity.getAds()));
        ads.setCount(userEntity.getAds().size());
        return ads;
    }
}
