package ru.skypro.homework.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class Ads {

    private static Integer count;
    private List<Ad> results;

}
