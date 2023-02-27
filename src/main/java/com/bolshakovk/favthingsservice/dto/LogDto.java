package com.bolshakovk.favthingsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LogDto {
    private LocalDateTime logDateTime;
    private String level;
    private String logger;
    private String message;
    private String username;
}
