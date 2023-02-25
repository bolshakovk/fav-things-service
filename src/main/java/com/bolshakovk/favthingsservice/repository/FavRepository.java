package com.bolshakovk.favthingsservice.repository;

import com.bolshakovk.favthingsservice.entity.Fav;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavRepository extends JpaRepository<Fav, Long> {
}
