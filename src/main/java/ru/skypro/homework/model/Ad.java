package ru.skypro.homework.model;

import lombok.*;
import ru.skypro.homework.dto.ExtendedAd;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Entity
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Integer pk;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String image;
    private String phone;
    private Integer price;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
    @OneToMany(mappedBy = "ad",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    List<Commentary> comments = new ArrayList<>();




}
