package com.example.sprlabs.Repository.Games;

import com.example.sprlabs.Classes.Developers;
import com.example.sprlabs.Classes.Games;
import com.example.sprlabs.Repository.Developers.DevelopersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class GameJDBCRepo implements GamesRepository {
    private final JdbcTemplate jdbcTemplate;
    private final DevelopersRepository developersRepository;

    @Autowired
    public GameJDBCRepo(JdbcTemplate jdbcTemplate, DevelopersRepository developersRepository) {
        this.developersRepository = developersRepository;
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Iterable<Games> findAll() {return jdbcTemplate.query("select * from games", this::mapRowToGames);}
    @Override
    public Optional<Games> findById(long id) {
        List<Games> results = jdbcTemplate.query("select * from games where id=?", this::mapRowToGames, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }
    private Games mapRowToGames(ResultSet row, int rowNum) throws SQLException {
        Developers select = developersRepository.findById(row.getInt("dev_id")).get();
        return new Games(row.getLong("id"), select.getId(), row.getString("name"),
                row.getTimestamp("release_year").toLocalDateTime().toLocalDate().toString(),
                row.getString("genre"), row.getString("system"),
                select);
    }
    @Override
    public Games addGames(Games games) {
        jdbcTemplate.update(
                "insert into games (dev_id, name, release_year, genre, system) " +
                        "values (?, ?, ?, ?, ?)",
                games.getDev().getId(),
                games.getName(),
                LocalDate.parse(games.getRelease_year()),
                games.getGenre(),
                games.getSystem());
        return games;
    }
    @Override
    public void deleteGames(long id){
        jdbcTemplate.update("delete from games where id=?", id);
    }
    @Override
    public Games updateGames(Games games){
        jdbcTemplate.update("update games set dev_id=?,name=?,release_year=?,genre=?,system=? where id=?",
                games.getDev().getId(),
                games.getName(),
                LocalDate.parse(games.getRelease_year()),
                games.getGenre(),
                games.getSystem(),
                games.getId());
        return games;
    }
}
