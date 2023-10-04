package ru.skypro.homework.service.mapping;

import org.hibernate.annotations.Parameter;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdMapping {

    public Ad adEntityToAdDto(ru.skypro.homework.model.Ad ad);

    public ExtendedAd adEntityToExtendedAdDto(ru.skypro.homework.model.Ad ad);

    public ru.skypro.homework.model.Ad adDtoToAdEntity(Ad ad);

    public ru.skypro.homework.model.Ad createOrUpdateAdDtoToAdEntity (CreateOrUpdateAd createOrUpdateAd);

}
