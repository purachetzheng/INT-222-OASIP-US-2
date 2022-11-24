//package sit.int221.oasipserver.token;
//
//import com.azure.spring.aad.webapi.AADResourceServerWebSecurityConfigurerAdapter;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//public class SecurityAzureConfig extends AADResourceServerWebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
//    }
//}
