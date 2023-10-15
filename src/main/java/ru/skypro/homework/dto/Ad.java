package ru.skypro.homework.dto;

public class Ad {

    private Integer author;
    private byte[] image;
    private Integer pk;
    private Integer price;
    private String title;

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
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
}
