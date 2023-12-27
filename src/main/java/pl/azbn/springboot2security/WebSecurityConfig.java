package pl.azbn.springboot2security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

//    public PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails userAdmin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("ADMIN") // By default if the supplied role does not start with ‘ROLE_’ it will be added.
                .build();
        UserDetails userUser = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(userAdmin, userUser);
    }
}
