package mx.admino;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService adminoUserDetails;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers(
                    "/js/**",
                    "/css/**",
                    "/images/**",
                    "/less/**"
            );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/condomino/**").hasRole("CONDOMINO")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/profile").authenticated()
                .antMatchers("/signup", "/restore", "/change").permitAll()
                .and()
            .formLogin()
                .defaultSuccessUrl("/condominio/panel")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(adminoUserDetails)
                .passwordEncoder(passwordEncoder());

/*        auth.inMemoryAuthentication()
            .withUser("user1").password(passwordEncoder().encode("123")).roles("CONDOMINO")
            .and()
            .withUser("user2").password(passwordEncoder().encode("123")).roles("ADMIN");*/
    }
}
