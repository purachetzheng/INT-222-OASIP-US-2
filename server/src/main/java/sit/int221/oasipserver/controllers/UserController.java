package sit.int221.oasipserver.controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sit.int221.oasipserver.dtos.user.*;
import sit.int221.oasipserver.entities.User;
import sit.int221.oasipserver.enums.UserRole;
import sit.int221.oasipserver.exception.PasswordException;
import sit.int221.oasipserver.services.UserService;
import sit.int221.oasipserver.token.AuthenticationResponse;
import sit.int221.oasipserver.token.CustomUserDetailsService;
import sit.int221.oasipserver.token.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired UserService userService;
    @Autowired private ModelMapper modelMapper;
    @Autowired CustomUserDetailsService userDetailsService;
    @Autowired JwtUtil jwtUtil;

    @GetMapping("")
    public List<UserDto> getAllUser() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserDetailDto getUserById(@PathVariable Integer id) {
        return modelMapper.map(userService.getById(id), UserDetailDto.class);
    }

    @PostMapping("")
    public UserDto createUser(@Valid @RequestBody PostUserDto newUser, BindingResult result) throws MethodArgumentNotValidException {
        return userService.create(newUser, result);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {userService.delete(id);}

    @PatchMapping("/{id}")
    public UserDto updateUser(
            @Valid @RequestBody PatchUserDto updateUser,
            @PathVariable Integer id,
            BindingResult result) throws MethodArgumentNotValidException {
        return userService.update(updateUser, id, result);
    }

//    @PostMapping("/match")
//    public User matchUserPassword(@Valid @RequestBody MatchUserDto matchUser) throws PasswordException {
//        return userService.match(matchUser);
//    }

//    @PostMapping("/match")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> matchUserPassword(@Valid @RequestBody MatchUserDto matchUser) throws PasswordException {
        return userService.match(matchUser);
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader("auth");
        final String token = authToken.substring(7);
        String username = jwtUtil.getUsernameFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.getUsernameFromToken(token));

        if (jwtUtil.canTokenBeRefreshed(token)) {
            String accessToken = jwtUtil.generateToken(userDetails);
            String refreshedToken = jwtUtil.refreshToken(token);
            return ResponseEntity.ok(new signInDto(accessToken, refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
