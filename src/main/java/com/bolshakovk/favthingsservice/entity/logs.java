package com.bolshakovk.favthingsservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class logs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private LocalDateTime logDateTime;
    private String level;
    private String logger;
    private String message;
    private String username;

}
