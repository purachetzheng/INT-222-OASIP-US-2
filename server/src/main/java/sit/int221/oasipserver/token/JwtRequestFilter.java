package sit.int221.oasipserver.token;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;
import sit.int221.oasipserver.entities.User;
import sit.int221.oasipserver.enums.UserRole;
import sit.int221.oasipserver.exception.ApiException;
import sit.int221.oasipserver.exception.PasswordException;
import sit.int221.oasipserver.repo.UserRepository;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;

//    @Autowired JwtDecoder jwtDecoder;

    private Cookie[] cookie;

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        final String authorizationHeader =  request.getHeader("auth");
//
//        String jwtToken = null;
//        String username = null;
//        String bearerToken = request.getHeader("auth");
//
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            jwtToken = bearerToken.substring(7, bearerToken.length());
//            username = jwtUtil.extractUsername(jwtToken);
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                if (jwtUtil.validateToken(jwtToken, userDetails)) {
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                }
//            }
//        }
//        filterChain.doFilter(request, response);
//    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        final String authorizationHeader =  request.getHeader("auth");
//
//        String jwtToken = null;
//        String username = null;
//        String bearerToken = request.getHeader("auth");
//        Cookie refreshCookie = WebUtils.getCookie(request, "refreshToken");
//        String refreshToken = "";
//        if(refreshCookie != null){
//            refreshToken = refreshCookie.getValue();
//        }
//        try {
//            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//                jwtToken = bearerToken.substring(7, bearerToken.length());
//                Jwt jwtDecoded = jwtDecoder.decode(jwtToken);
//                System.out.println(jwtDecoded);
////            if (StringUtils.hasText(bearerToken)) {
//                username = jwtUtil.extractUsername(jwtToken);
//                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                    if (jwtUtil.validateToken(jwtToken, userDetails)) {
//                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                    }
//                }
//            } else if(!refreshToken.equals("")) {
//                jwtToken = refreshToken;
//                username = jwtUtil.extractUsername(jwtToken);
//                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                    if (jwtUtil.validateToken(jwtToken, userDetails)) {
//                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                    }
//                }
//            }
//        } catch (ExpiredJwtException e) {
//            response.setContentType("application/json");
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getOutputStream().println("{ \"token\": \"" + "Token has expired" + "\" }");
//        }
//
//
//
//
//        filterChain.doFilter(request, response);
//    }
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final String authorizationHeader =  request.getHeader("auth");

    String jwtToken = null;
    String username = null;
    String bearerToken = request.getHeader("auth");
    Cookie refreshCookie = WebUtils.getCookie(request, "refreshToken");
    String refreshToken = "";
    if(refreshCookie != null){
        refreshToken = refreshCookie.getValue();
    }
    try {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            jwtToken = bearerToken.substring(7, bearerToken.length());
            DecodedJWT jwt = JWT.decode(jwtToken);
            System.out.println(jwt.getClaims());
//            if (StringUtils.hasText(bearerToken)) {
//            username = jwtUtil.extractUsername(jwtToken);
            if(!jwt.getClaim("preferred_username").toString().equals("Missing claim")){ //Azure Token
                System.out.println("Azure Token");
                username = jwt.getClaim("preferred_username").asString();
                if(!userRepository.existsByEmail(username)){
                    //create user in db
                    User user = new User();
                    user.setEmail(username);
                    user.setName(jwt.getClaim("name").asString());
                    user.setPassword(null);
                    user.setRole(UserRole.valueOf(jwt.getClaim("roles").asArray(String.class)[0]));
                    userRepository.saveAndFlush(user);
                }
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    System.out.println("loading");
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    //Set authentication with user detail
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                }
            } else { //Normal App Token
                System.out.println("Jwt App Token");
                username = jwtUtil.extractUsername(jwtToken);
                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    System.out.println("loading");
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtUtil.validateToken(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }

        } else if(!refreshToken.equals("") && !JWT.decode(refreshToken).getClaim("preferred_username").toString().equals("Missing Claim")) {
            System.out.println("refresh token filter");
            jwtToken = refreshToken;
            username = jwtUtil.extractUsername(jwtToken);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
    } catch (ExpiredJwtException e) {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println("{ \"token\": \"" + "Token has expired" + "\" }");
    }




    filterChain.doFilter(request, response);
}
}
