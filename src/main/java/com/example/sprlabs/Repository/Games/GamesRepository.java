package com.example.sprlabs.Repository.Games;

import com.example.sprlabs.Classes.Games;

import java.util.Optional;

public interface GamesRepository {
    Iterable<Games> findAll();
    Optional<Games> findById(long id);
    Games addGames(Games games);
    Games updateGames(Games games);
    void deleteGames(long id);

}
