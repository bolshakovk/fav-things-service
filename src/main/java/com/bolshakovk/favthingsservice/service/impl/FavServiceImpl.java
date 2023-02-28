package com.bolshakovk.favthingsservice.service.impl;

import com.bolshakovk.favthingsservice.entity.Fav;
import com.bolshakovk.favthingsservice.repository.FavRepository;
import com.bolshakovk.favthingsservice.service.FavService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FavServiceImpl implements FavService {
    private final FavRepository favRepository;
    @Override
    public void save(Fav favEntity) {
        favRepository.saveAndFlush(favEntity);
    }

}
