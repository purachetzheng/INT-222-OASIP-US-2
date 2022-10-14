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
import org.springframework.web.util.WebUtils;
import sit.int221.oasipserver.dtos.user.*;
import sit.int221.oasipserver.entities.User;
import sit.int221.oasipserver.enums.UserRole;
import sit.int221.oasipserver.exception.PasswordException;
import sit.int221.oasipserver.services.UserService;
import sit.int221.oasipserver.token.AuthenticationResponse;
import sit.int221.oasipserver.token.CustomUserDetailsService;
import sit.int221.oasipserver.token.JwtUtil;

import javax.servlet.http.Cookie;
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
    public List<UserDto> getAllUser(HttpServletRequest request) {
        return userService.getAll(request);
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

    @PostMapping("/login")
    public ResponseEntity<?> matchUserPassword(@Valid @RequestBody MatchUserDto matchUser, HttpServletResponse response) throws PasswordException {
        return userService.match(matchUser, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response){
//        Cookie refreshJwtCookie = WebUtils.getCookie(request, "refreshToken");
//        refreshJwtCookie.setMaxAge(0);
        Cookie deleteRefreshCookie = new Cookie("refreshToken", null);
        deleteRefreshCookie.setPath("/");
        deleteRefreshCookie.setMaxAge(0);
        response.addCookie(deleteRefreshCookie);
        return ResponseEntity.ok("Logout");
    }

    @PostMapping("/match")
    public UserDetailDto checkPassword(@Valid @RequestBody MatchUserDto matchUser) throws PasswordException {
        return modelMapper.map(userService.checkPassword(matchUser), UserDetailDto.class);
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader("auth");
        final String refreshToken = authToken.substring(7);
        String username = jwtUtil.getUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.getUsernameFromToken(refreshToken));

        if (jwtUtil.canTokenBeRefreshed(refreshToken)) {
            String accessToken = jwtUtil.generateToken(userDetails);
//            String refreshedToken = jwtUtil.refreshToken(token);
            return ResponseEntity.ok(new refreshDto(accessToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
