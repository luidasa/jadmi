package mx.admino;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import mx.admino.models.Condomino;
import mx.admino.services.CondominoService;

@SpringBootApplication
public class AdminoApplication implements CommandLineRunner {

	@Autowired
	CondominoService service;
	
	public static void main(String[] args) {
		SpringApplication.run(AdminoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		for(int i = 0; i < 51; i++) {
			String interior = "Casa - " + (i + 1);
			String nombre = UUID.randomUUID().toString();
			if (service.findByInterior(interior) == null) {
				service.save(new Condomino(interior, nombre));
			}
		}
	}
}
