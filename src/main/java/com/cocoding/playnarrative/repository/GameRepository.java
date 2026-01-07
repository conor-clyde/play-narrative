package com.cocoding.playnarrative.repository;

import com.cocoding.playnarrative.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}