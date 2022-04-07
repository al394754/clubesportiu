package es.uji.ei1027.clubesportiu;

import java.util.logging.Logger;

import es.uji.ei1027.clubesportiu.dao.NadadorDao;
import es.uji.ei1027.clubesportiu.dao.NadadorRowMapper;
import es.uji.ei1027.clubesportiu.model.Nadador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
public class ClubesportiuApplication implements CommandLineRunner {

	private static final Logger log = Logger.getLogger(ClubesportiuApplication .class.getName());

	public static void main(String[] args) {
		// Auto-configura l'aplicació
		new SpringApplicationBuilder(ClubesportiuApplication.class).run(args);
	}

	// Funció principal
	public void run(String... strings) throws Exception {
		/*log.info("Ací va el meu codi");
		log.info("Selecciona la nadadora Gemma Mengual");
		Nadador n1 = jdbcTemplate.queryForObject(
				"SELECT * FROM Nadador WHERE nom = 'Gemma Mengual'",
				new NadadorRowMapper());
		assert n1 != null;
		log.info(n1.toString());

		log.info("Selecciona la nadadora Gemma Mengual (amb paràmetre)");
		Nadador n2 = jdbcTemplate.queryForObject(
				"SELECT * FROM Nadador WHERE nom =?",
				new NadadorRowMapper(),
				"Gemma Mengual");
		assert n2 != null;
		log.info(n2.toString());

		log.info("Selecciona tots els nadadors");
		List<Nadador> nadadors = jdbcTemplate.query(
				"SELECT * FROM Nadador",
				new NadadorRowMapper());
		for (Nadador n: nadadors) {
			log.info(n.toString());
		}

		try {
			log.info("Selecciona una nadadora inexistent");
			Nadador n3 = jdbcTemplate.queryForObject(
					"SELECT * FROM Nadador WHERE nom = 'No estic'",
					new NadadorRowMapper());
			log.info(n3.toString());
		}
		catch(EmptyResultDataAccessException e) {
			log.info("No es troba a la base de dades");
		}

		log.info("Inserta una nova nadadora");
		jdbcTemplate.update(
				"INSERT INTO Nadador VALUES(?, ?, ?, ?, ?)",
				"Ariadna Edo", "XX1242", "Espanya", null, "Femení");
		log.info("I comprova que s'haja inserit correctament");
		mostraNadador("Ariadna Edo");

		log.info("Actualitza l'edat de la nadadora Ariadna Edo a 21 anys");
		jdbcTemplate.update("UPDATE Nadador SET edat=21 WHERE nom='Ariadna Edo'");
		log.info("I comprova que s'haja modificat correctament");
		mostraNadador("Ariadna Edo");

		log.info("Esborra la nadadora Ariadna Edo");
		jdbcTemplate.update("DELETE FROM Nadador WHERE nom='Ariadna Edo'");
		log.info("I comprova que s'haja esborrat correctament");
		mostraNadador("Ariadna Edo");
		provaNadadorDao();*/

	}

	void mostraNadador(String nomNadador){
		try {
			Nadador n4 = jdbcTemplate.queryForObject(
					"SELECT * FROM Nadador WHERE nom=?",
					new NadadorRowMapper(), nomNadador);
			log.info(n4.toString());
		}
		catch(EmptyResultDataAccessException e) {
			log.info("No es troba a la base de dades");
		}
	}

	// Plantilla per a executar operacions sobre la connexió
	private JdbcTemplate jdbcTemplate;

	// Crea el jdbcTemplate a partir del DataSource que hem configurat
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Autowired
	NadadorDao nadadorDao;

	/*void provaNadadorDao() {
		log.info("Provant NadadorDao");
		log.info("Tots els nadadors");

		for (Nadador n: nadadorDao.getNadadors()) {
			log.info(n.toString());
		}

		log.info("Dades de Gemma Mengual");
		Nadador n = nadadorDao.getNadador("Gemma Mengual");
		log.info(n.toString());

		Nadador aEdo = new Nadador();
		aEdo.setNom("Ariadna Edo");
		aEdo.setEdat(21);
		log.info("Nou: Ariadna Edo");
		nadadorDao.addNadador(aEdo);
		log.info(nadadorDao.getNadador("Ariadna Edo").toString());

		log.info("Actualitzat: Ariadna Edo");
		aEdo.setPais("Espanya");
		aEdo.setGenere("Femení");
		nadadorDao.updateNadador(aEdo);
		log.info(nadadorDao.getNadador("Ariadna Edo").toString());

		log.info("Esborrat: Ariadna Edo");
		nadadorDao.deleteNadador(aEdo);
		if (nadadorDao.getNadador("Ariadna Edo") == null) {
			log.info("Esborrada correctament");
		}
	}*/

}

