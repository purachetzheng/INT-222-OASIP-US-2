package sit.int221.oasipserver.token;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class MyAnonymousAuthenticationFilter extends AnonymousAuthenticationFilter {
    private static final String USER_SESSION_KEY = "user";
    private final String key;

    public MyAnonymousAuthenticationFilter(String key) {
        super(key);
        this.key = key;
    }

    @Override
    protected Authentication createAuthentication(HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        MyUserDetails user = Optional.ofNullable((MyUserDetails) httpSession.getAttribute(USER_SESSION_KEY))
                .orElseGet(() -> {
                    MyUserDetails anon = new MyUserDetails();
                    anon.setUsername("anonymousUser");
                    httpSession.setAttribute(USER_SESSION_KEY, anon);
                    return anon;
                });
        return new AnonymousAuthenticationToken(key, user, AuthorityUtils.createAuthorityList("ROLE_anonymous"));
    }
}
