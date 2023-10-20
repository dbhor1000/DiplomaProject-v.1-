package ru.skypro.homework.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;
import ru.skypro.homework.dto.Comment;

import javax.persistence.*;

@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Entity
public class Commentary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Nullable
    private Long createdAt;
    private String text;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity userRelated;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AD_ID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AdEntity adRelated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserEntity getUserRelated() {
        return userRelated;
    }

    public void setUserRelated(UserEntity userRelated) {
        this.userRelated = userRelated;
    }

    public AdEntity getAdRelated() {
        return adRelated;
    }

    public void setAdRelated(AdEntity adRelated) {
        this.adRelated = adRelated;
    }
}
