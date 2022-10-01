package sit.int221.oasipserver.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import sit.int221.oasipserver.entities.User;
import sit.int221.oasipserver.repo.UserRepository;

@Component
public class Guard {
    @Autowired
    private UserRepository repository;

    public boolean checkUserId(Authentication authentication, int id) {
        if(authentication.getAuthorities().toString().equals("[ROLE_student]")){
            System.out.println(authentication.getName());
            String email = authentication.getName();
            System.out.println(email + " at " + id);
            User result = repository.findByEmail(email);
            System.out.println(result.getId());
            return result != null && result.getId() == id;
        } else {
            System.out.println(id);
            System.out.println(authentication.getName());
            System.out.println(authentication.getAuthorities());
            System.out.println("Test");
            return true;
        }

    }
}
