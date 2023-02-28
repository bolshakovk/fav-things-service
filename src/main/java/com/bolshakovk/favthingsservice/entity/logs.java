package com.bolshakovk.favthingsservice.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class logs {
    @Id
    @Column(name = "log_date_time", nullable = false, length = 1000)
    private String log_date_time;

    private String level_info;
    private String logger;
    private String message;
    private String username;
}
