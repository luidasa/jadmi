package mx.admino;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import mx.admino.models.Condomino;
import mx.admino.services.CondominoService;

@SpringBootApplication
public class AdminoApplication implements Runnable{

	@Autowired
	CondominoService condominoService;
	
	public static void main(String[] args) {
		SpringApplication.run(AdminoApplication.class, args);
	}

	@Override
	public void run() {

		for(int i = 0; i < 51; i++) {
			condominoService.save(new Condomino("Casa " + i, "No registrado"));
			
		}
	}

}
