package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    public final ImageRepository imageRepository;
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Сервисный метод для вызова изображения из БД изображений по id.
     * @param id вызываемого изображения
     * @return DTO-object Image
     */

    @Override
    public Image callImageById (int id) {
        Image image = imageRepository.getReferenceById(id);
        return image;
    }

}
