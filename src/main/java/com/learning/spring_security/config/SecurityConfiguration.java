package com.learning.spring_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
//        http.authorizeHttpRequests((requests)->requests.anyRequest().permitAll());
        http.authorizeHttpRequests((requests)->requests
                .requestMatchers("/account","contact").authenticated()
                .requestMatchers("/welcome","/error").permitAll());
        http.formLogin(withDefaults());
       // http.formLogin(flc->flc.disable());//if you want to disable the form login
        http.httpBasic(withDefaults());
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService(){
       // UserDetails user = User.withUsername("shivam").password("{noop}shivam@").authorities("read").build();// when you don't use password encoder
        UserDetails user = User.withUsername("shivam").password("{bcrypt}$2a$12$gnTn0fDn/NlDhCYmGiGjXuNq1TugU0fA7zPfsy5vnJD7M14cReW3W").authorities("read").build(); // using passwordencoder
        UserDetails admin = User.withUsername("admin").password("{noop}12345").authorities("admin").build();
        return new InMemoryUserDetailsManager(user,admin);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }
    // to check if password is compromised in any data breach or not
//    @Bean
//    public CompromisedPasswordChecker compromisedPasswordChecker(){
//        return new HaveIBeenPwnedRestApiPasswordChecker();
//    }
}
