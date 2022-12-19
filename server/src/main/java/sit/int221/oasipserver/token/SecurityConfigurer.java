package sit.int221.oasipserver.token;

import com.azure.spring.aad.webapi.AADResourceServerWebSecurityConfigurerAdapter;
import com.azure.spring.autoconfigure.b2c.AADB2CJwtBearerTokenAuthenticationConverter;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import lombok.SneakyThrows;
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
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;
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

//        security.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated())
//                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(new AADB2CJwtBearerTokenAuthenticationConverter());

        security.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(this.unauthorizedHandler)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/users/{id}/**").access("@guard.checkUserId(authentication, #id)")
                .antMatchers("/authenticate", "/api/auth/login", "/signup", "/api/events/emailSender",
                        "/api/auth/logout", "/api/auth/test", "/api/guests", "/api/eventcategories", "/api/file/**"
                , "/api/tesst/hewkaw").permitAll()
                .antMatchers("/api/events/**", "/api/users/**").hasAnyRole("admin")
                .antMatchers("/api/events/**").hasAnyRole("student", "admin")
                .antMatchers("/api/events/**").hasAnyRole("student", "admin", "lecturer")
//                .antMatchers("/api/events").hasRole("lecturer")
                // .antMatchers(HttpMethod.POST, "/api/users")
                // .antMatchers(HttpMethod.POST, "/us2/api/users").permitAll()
//                .anyRequest().authenticated().and().oauth2ResourceServer().jwt().jwtAuthenticationConverter(new AADB2CJwtBearerTokenAuthenticationConverter());
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

//    @Bean
//    public JwtDecoder jwtDecoder() {
//        return new JwtDecoder() {
//            @Override
//            public Jwt decode(String token) throws JwtException {
//                return null;
//            }
//        };
//    }

//    @Bean
//    public JwtDecoderFactory<ClientRegistration> jwtDecoderFactory() {
//
//        final JwtDecoder decoder = new JwtDecoder() {
//
//            @SneakyThrows
//            @Override
//            public Jwt decode(String token) throws JwtException {
//                JWT jwt = JWTParser.parse(token);
//                return createJwt(token, jwt);
//            }
//
//            private Jwt createJwt(String token, JWT parsedJwt) {
//                try {
//                    Map<String, Object> headers = new LinkedHashMap<>(parsedJwt.getHeader().toJSONObject());
//                    Map<String, Object> claims = parsedJwt.getJWTClaimsSet().getClaims();
//                    return Jwt.withTokenValue(token)
//                            .headers(h -> h.putAll(headers))
//                            .claims(c -> c.putAll(claims))
//                            .build();
//                } catch (Exception ex) {
//                    if (ex.getCause() instanceof ParseException) {
//                        throw new JwtException(String.format( "Malformed payload"));
//                    } else {
//                        throw new JwtException(String.format(ex.getMessage()), ex);
//                    }
//                }
//            }
//        };
//        return context -> decoder;
//    }

    @Bean
    JwtDecoder jwtDecoder() {
        return  new CustomJwtDecoder();
    }

    class CustomJwtDecoder implements JwtDecoder {
        @Override
        public Jwt decode(String token) throws JwtException {
            System.out.println(token);
            return null;
        }
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
