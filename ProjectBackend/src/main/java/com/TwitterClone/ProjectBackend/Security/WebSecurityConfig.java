package com.TwitterClone.ProjectBackend.Security;

import com.TwitterClone.ProjectBackend.Service.LoadUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * In this method we disable the CSRF protection for know so our app can accept any request from any user.
     * @param http
     * @throws Exception
     */

    private final LoadUserService loadUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    protected void configure(HttpSecurity http) throws Exception {

        //CRSF tokens consigure:
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v*/registration/**")
                .permitAll()
                .anyRequest()
                .authenticated().and()
                .formLogin();

     /*   // Public pages
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/error").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/signUp").permitAll();
        http.authorizeRequests().antMatchers("../templates/explore").permitAll();
        http.authorizeRequests().antMatchers("/reset-password").permitAll();
        http.authorizeRequests().antMatchers("/forgot-password").permitAll();

        // Private pages
        http.authorizeRequests().antMatchers("/write-Tweet").hasAnyRole("REGISTEREDUSER");
        http.authorizeRequests().antMatchers("../templates/notifications").hasAnyRole("REGISTEREDUSER");
        http.authorizeRequests().antMatchers("/bookmarks").hasAnyRole("REGISTEREDUSER");
        http.authorizeRequests().antMatchers("../templates/home").hasAnyRole("REGISTEREDUSER");
        http.authorizeRequests().antMatchers("/profile").hasAnyRole("REGISTEREDUSER");

        http.authorizeRequests().antMatchers("/adminpanel").hasAnyRole("ADMIN");

        // Login form
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("../templates/home");
        http.formLogin().failureUrl("/error");

        // Logout
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");*/
    }

    protected void configure(AuthenticationManagerBuilder auth){
        auth.authenticationProvider(AuthenticationProvider());
    }
    @Bean
    public AuthenticationProvider AuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(loadUserService);
        return provider;
    }
}
