package ru.skypro.homework.service.mapping;

import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.model.UserEntity;

import java.util.List;

public interface AdMapping {

    public List<Ad> adEntityListToAdsDto(List<AdEntity> inputAdList);

    //Этот маппер не нужен(/сломался/требует внимания/выглядит грустным/(был) слишком хорош :P)?
    Ad adEntityToAdDto(AdEntity ad);

    public ExtendedAd adEntityToExtendedAdDto(AdEntity ad);

    //public ru.skypro.homework.model.Ad adDtoToAdEntity(Ad ad);

    public AdEntity createOrUpdateAdDtoToAdEntity (CreateOrUpdateAd createOrUpdateAd);
    public Ads userAdsToAdsDTO(UserEntity userEntity);

}
