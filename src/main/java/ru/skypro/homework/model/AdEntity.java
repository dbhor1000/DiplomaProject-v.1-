package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Entity
public class  AdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //Главный ключ сооветствует полю Integer pk в DTO*
    private String description;
    private Integer price;
    private String title;
    @OneToOne
    @JoinColumn(name = "AD_IMAGE", nullable = true)
    private Image imageAd;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "USER_ID", nullable = true)
    private UserEntity userRelated;
    @OneToMany(mappedBy = "adRelated",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    List<Commentary> comments = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserEntity getUserRelated() {
        return userRelated;
    }

    public void setUserRelated(UserEntity userRelated) {
        this.userRelated = userRelated;
    }

    public List<Commentary> getComments() {
        return comments;
    }

    public void setComments(List<Commentary> comments) {
        this.comments = comments;
    }

    public Image getImageAd() {
        return imageAd;
    }

    public void setImageAd(Image imageAd) {
        this.imageAd = imageAd;
    }
}
