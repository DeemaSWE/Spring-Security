package com.example.security.SecurityConfig;

import com.example.security.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider dawAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(dawAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/register").permitAll()
                .requestMatchers("/api/v1/auth/get-all").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/auth/delete/**").hasAuthority("ADMIN")
                .requestMatchers("api/v1/todo/get-all").hasAuthority("ADMIN")
                .requestMatchers("api/v1/todo/get-my-todos", "api/v1/todo/add", "api/v1/todo/update/**", "api/v1/todo/delete/**").hasAuthority("CUSTOMER")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();

    }

    //  .authenticationProvider(dawAuthenticationProvider()) //for custom authentication provider

    //  .requestMatchers("/api/v1/auth/register", "/api/v1/auth/login") //for specific requests

    //  .requestMatchers("/api/v1/auth/**") //for all

    // .requestMatchers(HttpMethod.POST,"/api/v1/auth/register") //for specific method

    // .anyRequest().permitAll() //for other requests

    // .httpBasic(); //authorization basic auth

    // .hasAuthority("ADMIN") //for specific authority

    // .authenticated() //for all authenticated users

    // .hasAnyAuthority("ADMIN", "USER") //for multiple authorities

    // .permitAll() //for all users

    // .denyAll() //for no one

    // .hasRole("ADMIN") //for specific role

    // .hasAnyRole("ADMIN", "USER") //for multiple roles

}
