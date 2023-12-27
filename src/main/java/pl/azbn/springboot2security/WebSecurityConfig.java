package pl.azbn.springboot2security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //user management
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails userAdmin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("pass")
                .roles("ADMIN") // By default if the supplied role does not start with ‘ROLE_’ it will be added.
                .build();
        UserDetails userUser = User.withDefaultPasswordEncoder()
                .username("user")
                .password("pass")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(userAdmin, userUser);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.
                authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/forAdmin").hasRole("ADMIN")
                        .requestMatchers("/forUser").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .permitAll())
                .httpBasic(withDefaults())
                .formLogin(withDefaults());
        return http.build();
    }
}

//authorization mechanism
