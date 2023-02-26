package com.bolshakovk.favthingsservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Fav {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String favDish;

    private String favSong;

    private String favDate;
    private Integer favDigit;

    private String favColor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User usr;

    public Fav(String favDish, String favSong, Integer favDigit, String favDate, String favColor, User user) {
        this.usr = user;
        this.favDish = favDish;
        this.favSong = favSong;
        this.favDigit = favDigit;
        this.favDate = favDate;
        this.favColor = favColor;
    }

    public Fav() {

    }
}
