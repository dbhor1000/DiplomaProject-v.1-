//package ru.skypro.homework.model;
//
//import lombok.*;
//import ru.skypro.homework.dto.Comment;
//import ru.skypro.homework.dto.Role;
//
//import javax.persistence.*;
//import javax.validation.constraints.Size;
//import java.util.ArrayList;
//import java.util.List;
//
//@NoArgsConstructor
//@Getter
//@Setter
//@EqualsAndHashCode
//@AllArgsConstructor
//@Entity
//p//ublic class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//    private String email;
//    private String firstName;
//    private String lastName;
//    private String phone;
//    private Role role;
//    private String image;
//    private String username;
//    private String password;
//
//    @OneToMany(mappedBy = "userRelated",
//                fetch = FetchType.LAZY,
//                cascade = CascadeType.ALL)
//    List<Ad> ads = new ArrayList<>();
//
//    @OneToMany(mappedBy = "userRelated",
//            fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    List<Ad> comments = new ArrayList<>();
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public List<Ad> getAds() {
//        return ads;
//    }
//
//    public void setAds(List<Ad> ads) {
//        this.ads = ads;
//    }
//
//    public List<Ad> getComments() {
//        return comments;
//    }
//
//    public void setComments(List<Ad> comments) {
//        this.comments = comments;
//    }
//}
