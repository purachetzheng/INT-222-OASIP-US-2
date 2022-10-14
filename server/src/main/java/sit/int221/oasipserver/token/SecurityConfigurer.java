package sit.int221.oasipserver.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.UUID;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Autowired
    private RestAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private CustomUserDetailsService myUserDetailService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER").and()
//                .withUser("admin").password("password").roles("USER", "ADMIN");
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(myUserDetailService);
    }

    @Override
    public void configure(HttpSecurity security) throws Exception {
        security.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(this.unauthorizedHandler)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/users/{id}/**").access("@guard.checkUserId(authentication, #id)")
                .antMatchers("/authenticate", "/api/auth/login", "/signup", "/api/events/emailSender",
                        "/api/auth/logout", "/api/auth/test").permitAll()
                .antMatchers("/api/events/**", "/api/users/**").hasAnyRole("admin")
                .antMatchers("/api/events/**").hasAnyRole("student", "admin")
                .antMatchers("/api/events/**").hasAnyRole("student", "admin", "lecturer")
//                .antMatchers("/api/events").hasRole("lecturer")
                // .antMatchers(HttpMethod.POST, "/api/users")
                // .antMatchers(HttpMethod.POST, "/us2/api/users").permitAll()
                .anyRequest().authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        security.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web)
            throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/api/users")
                .antMatchers(HttpMethod.POST, "/us2/api/users");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Argon2PasswordEncoder();
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
