package sit.int221.oasipserver.token;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sit.int221.oasipserver.exception.type.ApiNotFoundException;
import sit.int221.oasipserver.repo.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository jwtUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(jwtUserRepository.existsByEmail(email)){
            sit.int221.oasipserver.entities.User jwtUser = jwtUserRepository.findByEmail(email);
            return new User(jwtUser.getEmail(), jwtUser.getPassword(), getAuthority(jwtUser));
        } else {
            throw new ApiNotFoundException("Email does not exist");
        }


//        if (jwtUser == null) {
//            throw new UsernameNotFoundException("email Not found" + email);
//        }

    }

    private Set<SimpleGrantedAuthority> getAuthority(sit.int221.oasipserver.entities.User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
    }

}
