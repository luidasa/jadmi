package mx.admino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Administración de condominios. Clase de inicio.
 */
@SpringBootApplication
@EnableScheduling
public class Application {

	/**
	 * Metodo de inicio de la aplicación
	 * @param args
	 */
	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
