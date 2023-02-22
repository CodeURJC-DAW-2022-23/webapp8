package Security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

public class Configure extends WebSecurityConfiguration {


    protected void configure(HttpSecurity http) throws Exception {

        // Public pages
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
        http.logout().logoutSuccessUrl("/");
    }
}
