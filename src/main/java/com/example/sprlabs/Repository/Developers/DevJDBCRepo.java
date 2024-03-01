package com.example.sprlabs.Repository.Developers;

import com.example.sprlabs.Classes.Developers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class DevJDBCRepo implements DevelopersRepository {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public DevJDBCRepo(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    public Iterable<Developers> findAll() {
        return jdbcTemplate.query("select * from developers", this::mapRowToDev);
    }

    @Override
    public Optional<Developers> findById(long id){
        List<Developers> result = jdbcTemplate.query("select * from developers where id = ?", this::mapRowToDev, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    private Developers mapRowToDev(ResultSet row, int rowNum) throws SQLException {
        return new Developers(row.getLong("id"), row.getString("name"),
                row.getFloat("rating"));
    }

    @Override
    public Developers addDev(Developers developers) {
        jdbcTemplate.update("insert into developers (name, rating) values (?, ?)",
                developers.getName(),
                developers.getRating());
        return developers;
    }

    @Override
    public void deleteDev(long id){
        jdbcTemplate.update("delete from developers where id=?", id);
    }

    @Override
    public Developers updateDev(Developers developers){
        jdbcTemplate.update("update developers set name=?,rating=? where id=?",
                developers.getName(), developers.getRating(), developers.getId());
        return developers;
    }
}
