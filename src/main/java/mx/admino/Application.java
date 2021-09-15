package mx.admino;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import mx.admino.models.Condomino;
import mx.admino.services.CondominoService;

@SpringBootApplication
public class Application  extends WebSecurityConfigurerAdapter implements CommandLineRunner {

	@Autowired
	CondominoService service;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	// @formatter:off
        http
            .authorizeRequests(a -> a
                .antMatchers("/", "/error").permitAll()
                .anyRequest().authenticated()
            )
            .exceptionHandling(e -> e
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .oauth2Login();
        // @formatter:on
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
