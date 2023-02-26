package com.bolshakovk.favthingsservice.service;

import com.bolshakovk.favthingsservice.entity.Fav;
import com.bolshakovk.favthingsservice.entity.User;

public interface FavService {
    void save(Fav favEntity);
    void delete(Fav favEntity);
}
