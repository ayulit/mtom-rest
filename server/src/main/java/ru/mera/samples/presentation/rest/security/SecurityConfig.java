package ru.mera.samples.presentation.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication()
                .withUser("rest").password("rocks").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN","USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/images/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .httpBasic();
                
                
        http
            .formLogin()
                .defaultSuccessUrl("/users/login?success=true", true)
            .and()
            .logout()
                .permitAll();

    }    
    
}
