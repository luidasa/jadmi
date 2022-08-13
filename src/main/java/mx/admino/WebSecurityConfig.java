package mx.admino;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserDetailsService userDetailsService;

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(@Autowired AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
          .passwordEncoder(bCryptPasswordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
	        .authorizeRequests()
	    	.antMatchers("/","/signup","/recover", "/reset").permitAll()
	    	.antMatchers("/condomino").authenticated()
	    	.antMatchers("/admin/*").hasRole("ADMIN").and()
	        .formLogin()
	        .loginPage("/login")
	        .permitAll()
            .defaultSuccessUrl("/panel", true)
            .and()
	        .logout() // Metodo get pues he desabilitado CSRF
	        .permitAll();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {

        web
            .ignoring()
            .antMatchers(
                    "/bootstrap/**",
	                "/plugins/**",
	                "/js/**",
	                "/fonts/**",
	                "/css/**",
	                "/images/**",
	                "/less/**"
            );
    }
}
