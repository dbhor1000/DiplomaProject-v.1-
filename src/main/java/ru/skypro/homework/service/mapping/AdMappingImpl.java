package ru.skypro.homework.service.mapping;

import org.mapstruct.Mapper;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.repository.UserRepository;


public class AdMappingImpl implements AdMapping {

    UserRepository userRepository;


    public AdMappingImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Этот маппер не нужен(/сломался/требует внимания/выглядит грустным/(был) слишком хорош :P)?
    @Override
    public Ad AdEntityToAdDto(ru.skypro.homework.model.Ad ad) {
        if (ad == null) {
            return null;
        }
        Ad assembledAdDto = new Ad();
        assembledAdDto.setAuthor(ad.getUser().getId());
        assembledAdDto.setPk(ad.getPk());
        assembledAdDto.setImage(ad.getImage());
        assembledAdDto.setPrice(ad.getPrice());
        assembledAdDto.setTitle(ad.getTitle());
        return assembledAdDto;
    }
    @Override
    public ExtendedAd AdEntityToExtendedAdDto(ru.skypro.homework.model.Ad ad) {
        if (ad == null) {
            return null;
        }
        ExtendedAd assembledExtendedAdDto = new ExtendedAd();
        assembledExtendedAdDto.setPk(ad.getPk());
        assembledExtendedAdDto.setAuthorFirstName(ad.getUser().getFirstName());
        assembledExtendedAdDto.setAuthorLastName(ad.getUser().getLastName());
        assembledExtendedAdDto.setDescription(ad.getDescription());
        assembledExtendedAdDto.setEmail(ad.getUser().getEmail());
        assembledExtendedAdDto.setImage(ad.getImage());
        assembledExtendedAdDto.setPhone(ad.getUser().getPhone());
        assembledExtendedAdDto.setPrice(ad.getPrice());
        assembledExtendedAdDto.setTitle(ad.getTitle());
        return assembledExtendedAdDto;

    }

    @Override
    public ru.skypro.homework.model.Ad AdDtoToAdEntity(Ad ad){
        if (ad == null) {
            return null;
        }

        ru.skypro.homework.model.Ad modifiedAdEntity = new ru.skypro.homework.model.Ad();
        modifiedAdEntity.setUser(userRepository.getReferenceById(Long.valueOf(ad.getAuthor())));
        modifiedAdEntity.setPk(ad.getPk());
        modifiedAdEntity.setImage(ad.getImage());
        modifiedAdEntity.setPrice(ad.getPrice());
        modifiedAdEntity.setTitle(ad.getTitle());
        return modifiedAdEntity;

    }

    @Override
    public ru.skypro.homework.model.Ad CreateOrUpdateAdDtoToAdEntity (CreateOrUpdateAd createOrUpdateAd){
        if (createOrUpdateAd == null) {
            return null;
        }

        ru.skypro.homework.model.Ad modifiedAdEntity = new ru.skypro.homework.model.Ad();
        modifiedAdEntity.setPrice(createOrUpdateAd.getPrice());
        modifiedAdEntity.setTitle(createOrUpdateAd.getTitle());
        modifiedAdEntity.setDescription(createOrUpdateAd.getDescription());
        return modifiedAdEntity;

    }



}
