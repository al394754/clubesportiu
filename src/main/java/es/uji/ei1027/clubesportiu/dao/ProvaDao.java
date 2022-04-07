package es.uji.ei1027.clubesportiu.dao;
import es.uji.ei1027.clubesportiu.model.Nadador;
import es.uji.ei1027.clubesportiu.model.Prova;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository // En Spring els DAOs van anotats amb @Repository

public class ProvaDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addProva(Prova prova) {
        jdbcTemplate.update("INSERT INTO Prova VALUES(?, ?, ?, ?)",
                prova.getNom(), prova.getDescripcio(), prova.getTipus(), prova.getData());
    }

    public void deleteProva(Prova prova) {
        jdbcTemplate.update("DELETE FROM Prova WHERE nom=?",
                prova.getNom());
    }

    public void updateProva(Prova prova) {
        jdbcTemplate.update("UPDATE Prova SET descripcio=?, tipus=?, data=? WHERE nom=?",
                prova.getDescripcio(), prova.getTipus(), prova.getData(), prova.getNom());
    }

    public Prova getProva(String prova) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Prova WHERE nom=?",
                    new ProvaRowMapper(), prova);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Prova> getProves() {
        try {
            return jdbcTemplate.query("SELECT * FROM Prova",
                    new ProvaRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Prova>();
        }
    }

    public void run(){

    }
}
