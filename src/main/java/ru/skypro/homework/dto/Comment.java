package ru.skypro.homework.dto;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;


@Data
public class Comment {

    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Long createdAt;
    //Исправил исходя из комментария на GitHub. Однако, в Schemas был Integer.
    private Integer pk;
    private String text;

}
