package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class CreateOrUpdateAd {

    @Size(min = 4, max = 32, message = "Проверьте количество символов.")
    private String title;
    @Min(value = 0 , message = "Значение должно быть больше 0")
    @Max(value = 10000000 , message = "Значение должно быть меньше 10000000")
    private Integer price;
    @Size(min = 8, max = 64, message = "Проверьте количество символов.")
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
