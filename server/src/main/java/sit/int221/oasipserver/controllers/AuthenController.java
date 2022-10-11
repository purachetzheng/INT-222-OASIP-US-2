package sit.int221.oasipserver.controllers;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;
import sit.int221.oasipserver.dtos.user.MatchUserDto;
import sit.int221.oasipserver.dtos.user.UserDetailDto;
import sit.int221.oasipserver.dtos.user.UserDetailDtoImpl;
import sit.int221.oasipserver.dtos.user.refreshDto;
import sit.int221.oasipserver.entities.User;
import sit.int221.oasipserver.exception.PasswordException;
import sit.int221.oasipserver.repo.UserRepository;
import sit.int221.oasipserver.services.UserService;
import sit.int221.oasipserver.token.CustomUserDetailsService;
import sit.int221.oasipserver.token.JwtUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CustomUserDetailsService userDetailsService;


//    private Cookie[] cookie;

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

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
//        String authToken = request.getHeader("auth");
//        final String refreshToken = authToken.substring(7);
//        cookie = request.getCookies();
//        String refreshToken = Arrays.stream(cookie).map(cookie1 -> cookie1.getValue()).toString();
//        String username = jwtUtil.getUsernameFromToken(refreshToken);

        Cookie refreshCookie = WebUtils.getCookie(request, "refreshToken");
        String refreshToken = "";

        if(refreshCookie != null){
            refreshToken = refreshCookie.getValue();
        }
        System.out.println(refreshToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.getUsernameFromToken(refreshToken));

        if (jwtUtil.canTokenBeRefreshed(refreshToken)) {
            String accessToken = jwtUtil.generateToken(userDetails);
//            String refreshedToken = jwtUtil.refreshToken(token);
            return ResponseEntity.ok(new refreshDto(accessToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserId(HttpServletRequest request) {
        Cookie refreshCookie = WebUtils.getCookie(request, "refreshToken");
        String refreshToken = "";

        if(refreshCookie != null){
            refreshToken = refreshCookie.getValue();
        }

        Claims claims = jwtUtil.getClaim(refreshToken);

        Integer userId = Integer.parseInt(claims.get("userId").toString());

        User user = userService.getById(userId);

        return ResponseEntity.ok(new UserDetailDtoImpl(user.getName(), user.getEmail(), user.getRole()));
    }
}
