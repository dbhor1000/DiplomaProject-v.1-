package ru.skypro.homework.dto;

import lombok.Data;

import java.util.ArrayList;


@Data
public class Ads {

    private static Integer count;
    private ArrayList<Ad> results;

    {
        count = results.size() - 1;
    }

    //Тут не совсем уверен по поводу static и вообще в необходимости инициализации поля count

}
