package sit.int221.oasipserver.token;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sit.int221.oasipserver.exception.type.ApiNotFoundException;
import sit.int221.oasipserver.repo.UserRepository;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository jwtUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(jwtUserRepository.existsByEmail(email)){
            sit.int221.oasipserver.entities.User jwtUser = jwtUserRepository.findByEmail(email);
            return new User(jwtUser.getEmail(), jwtUser.getPassword(), new ArrayList<>());
        } else {
            throw new ApiNotFoundException("Email does not exist");
        }


//        if (jwtUser == null) {
//            throw new UsernameNotFoundException("email Not found" + email);
//        }

    }
}
