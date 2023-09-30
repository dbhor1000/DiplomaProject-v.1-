package ru.skypro.homework.service.mapping;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;

@Mapper
public interface AdMapping {

    public Ad AdEntityToAdDto(ru.skypro.homework.model.Ad ad);

    public ExtendedAd AdEntityToExtendedAdDto(ru.skypro.homework.model.Ad ad);

    public ru.skypro.homework.model.Ad AdDtoToAdEntity(Ad ad);

    public ru.skypro.homework.model.Ad CreateOrUpdateAdDtoToAdEntity (CreateOrUpdateAd createOrUpdateAd);

}
