package ru.skypro.homework.model;

import lombok.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;
    private String username;
    private String password;

    @OneToMany(mappedBy = "user",
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    List<Ad> ads = new ArrayList<>();

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    List<Ad> comments = new ArrayList<>();



}
