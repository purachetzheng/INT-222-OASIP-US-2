package sit.int221.oasipserver.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sit.int221.oasipserver.repo.UserRepository;


@RestController
@RequestMapping("/api/tesst")
public class TestController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/hewkaw")
    @ResponseBody
    @PreAuthorize("hasAuthority('SCOPE_access_as_user')")
    //    public String date(BearerTokenAuthentication bearerTokenAuth) {
//        OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) bearerTokenAuth.getPrincipal();
//        return principal.getAttribute("scp").toString();
//    }
    public boolean test(){
        return userRepository.existsByEmail("yuthasart.oat@mail.kmutt.ac.th");
    }

}
