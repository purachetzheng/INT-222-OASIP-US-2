package sit.int221.oasipserver.controllers;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;
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
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.ZoneOffset.UTC;

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

    @GetMapping("/test")
    public String test(TimeZone timeZone, HttpServletRequest request){
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd, yyyy HH:mm", Locale.ENGLISH);
        formatter.setTimeZone(timeZone);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEE MMM dd, yyyy HH:mm", Locale.ENGLISH).withZone(ZoneId.of("Asia/Bangkok"));

        String zoneId = timeZone.getID();
        System.out.println(timeZone.getID());

        Instant instant = Instant.now();
        System.out.println("Instant UTC: " + instant.atZone(ZoneId.of("UTC")));
        System.out.println("Instant NOW: " + instant);

        ZonedDateTime bkTime = instant.atZone(ZoneId.of(zoneId));
        System.out.println("Instant LOCAL: " + bkTime);
        System.out.println("Instant LOCAL: " + bkTime.toInstant());
        System.out.println(formatter.format(Date.from(bkTime.toInstant())));




        return timeZone.toString();
    }
}
