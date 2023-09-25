package ru.skypro.homework.dto;

import lombok.Data;

import java.util.ArrayList;


@Data
public class Comments {

    private Integer count;

    private ArrayList<Comments> results;

    {
        count = results.size() - 1;
    }

    //Аналогично с Ads

}
