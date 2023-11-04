package ru.skypro.homework.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] image;
    @OneToOne(mappedBy = "imageAvatar")
    private UserEntity userEntity;
    @OneToOne(mappedBy = "imageAd")
    private AdEntity ad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
