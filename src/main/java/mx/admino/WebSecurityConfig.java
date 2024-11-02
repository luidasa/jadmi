package mx.admino;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    UserDetailsService adminoUserDetails;
    
    @Bean
    PasswordEncoder getPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(requests -> requests
                .requestMatchers("/perfil", "/condominios/**", "/condominos/**", "/pagos/**", "/cuotas/**", "/").authenticated()
                .requestMatchers("/login", "/signup", "/restore", "/change").permitAll())
        .csrf(csrf -> csrf.disable())
        .formLogin(login -> login
                .loginPage("/login").failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/condominios")
                .permitAll())
        .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll());

        return http.build();
    }
}
