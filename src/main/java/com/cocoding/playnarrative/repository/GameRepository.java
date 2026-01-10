package com.cocoding.playnarrative.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocoding.playnarrative.model.Game;
import com.cocoding.playnarrative.model.PlayState;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByPlayState(PlayState playState);

    List<Game> findByOwned(boolean owned);

    List<Game> findByOwnedAndPlayState(boolean owned, PlayState playState);

    Optional<Game> findByExternalId(String externalId);
}