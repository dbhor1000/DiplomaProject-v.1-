package ru.skypro.homework.service.mapping;

import org.hibernate.annotations.Parameter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.model.UserEntity;

import java.util.List;

//@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdMapping {

    public List<Ad> AdEntityListToAdsDto(List<ru.skypro.homework.model.Ad> inputAdList);

    public Ad adEntityToAdDto(ru.skypro.homework.model.Ad ad);

    public ExtendedAd adEntityToExtendedAdDto(ru.skypro.homework.model.Ad ad);

    public ru.skypro.homework.model.Ad adDtoToAdEntity(Ad ad);

    public ru.skypro.homework.model.Ad createOrUpdateAdDtoToAdEntity (CreateOrUpdateAd createOrUpdateAd);
    public Ads userAdsToAdsDTO(UserEntity userEntity);

}
