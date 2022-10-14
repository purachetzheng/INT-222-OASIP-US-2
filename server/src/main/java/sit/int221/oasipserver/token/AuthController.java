package sit.int221.oasipserver.token;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int221.oasipserver.dtos.user.MatchUserDto;
import sit.int221.oasipserver.dtos.user.PostUserDto;
import sit.int221.oasipserver.entities.User;
import sit.int221.oasipserver.enums.UserRole;
import sit.int221.oasipserver.exception.PasswordException;
import sit.int221.oasipserver.exception.type.ApiRequestException;
import sit.int221.oasipserver.repo.UserRepository;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired private UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    Argon2 argon2 = Argon2Factory.create(
            Argon2Factory.Argon2Types.ARGON2id,
            10,
            20);

    @RequestMapping("/hello")
    public String hello(){
        return  "Nil here";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody PostUserDto signUpRequest) {


        if(repository.findByEmail(signUpRequest.getEmail()) != null) {
            throw new ApiRequestException("Email already exist");
        }

        // Creating user's account
        User jwtUser = new User();
        jwtUser.setName(signUpRequest.getName());
        jwtUser.setEmail(signUpRequest.getEmail());
        jwtUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        jwtUser.setRole(UserRole.valueOf(signUpRequest.getRole()));
        repository.save(jwtUser);
        return ResponseEntity.ok("successfully created");
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody MatchUserDto loginRequest) throws PasswordException {
//        User user = repository.findByEmail(loginRequest.getEmail());
//        String userArgon2Password = user.getPassword();
//
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginRequest.getEmail(),
//                            loginRequest.getPassword()
//                    )
//            );
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
//
//            final String jwt = jwtUtil.generateToken(userDetails);
//
//            return ResponseEntity.ok(new AuthenticationResponse(jwt));
//
//
//
//
//    }
}
