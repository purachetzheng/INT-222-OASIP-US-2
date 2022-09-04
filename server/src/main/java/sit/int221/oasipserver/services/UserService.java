package sit.int221.oasipserver.services;

import com.google.common.hash.Hashing;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestMapping;
import sit.int221.oasipserver.dtos.user.MatchUserDto;
import sit.int221.oasipserver.dtos.user.PatchUserDto;
import sit.int221.oasipserver.dtos.user.PostUserDto;
import sit.int221.oasipserver.dtos.user.UserDto;
import sit.int221.oasipserver.entities.User;
import sit.int221.oasipserver.exception.PasswordException;
import sit.int221.oasipserver.exception.type.ApiNotFoundException;
import sit.int221.oasipserver.repo.UserRepository;
import sit.int221.oasipserver.utils.RoleValidate;
import sit.int221.oasipserver.utils.ListMapper;
import sit.int221.oasipserver.enums.UserRole;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired private UserRepository repository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private ListMapper listMapper;
    @Autowired private RoleValidate roleValidate;
    Argon2 argon2 = Argon2Factory.create(
            Argon2Factory.Argon2Types.ARGON2id,
            10,
            20);

    //nameError
    final private FieldError nameErrorObj = new FieldError("createUserDto",
            "name", "Name already exist");
    //emailError
    final private FieldError emailErrorObj = new FieldError("createUserDto",
            "email", "Email already exist");
    //roleError
    final private FieldError roleErrorObj = new FieldError("createUserDto",
            "role", "UserRole must be specific as 'student' or 'admin' or 'lecturer");
    //roleError
    final private FieldError passwordErrorObj = new FieldError("matchUserDto",
            "password", "password DOES NOT match");

    //Get All
    public List<UserDto> getAll() {
        List<User> userList = repository.findAllByOrderByNameAsc();
        return listMapper.mapList(userList, UserDto.class, modelMapper);
    }

    //Get By ID
    public User getById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ApiNotFoundException("User id " + id + "Does Not Exist !!!"));
    }

    //Delete
    public void delete(Integer id) {
        repository.delete(getById(id));
    }

    //Insert
    public UserDto create(PostUserDto newUser, BindingResult result) throws MethodArgumentNotValidException {
//        String sha256hex = Hashing.sha256()
//                .hashString(newUser.getPassword(), StandardCharsets.UTF_8)
//                .toString();
//        String sha256hex = sha256(newUser.getPassword());
        
        String name = newUser.getName();
        String email = newUser.getEmail();
        String password = newUser.getPassword();
        if(name != null)
            newUser.setName(newUser.getName().trim());
        if(email != null)
            newUser.setEmail(newUser.getEmail().trim());
        if(password != null){
            String hashPassword = argon2.hash(2, 150, 1, newUser.getPassword());
            newUser.setPassword(hashPassword);
        }

        if (repository.existsByName(newUser.getName()))
            result.addError(nameErrorObj);
        if (repository.existsByEmail(newUser.getEmail()))
            result.addError(emailErrorObj);

        if (result.hasErrors()) throw new MethodArgumentNotValidException(null, result);

        String role = newUser.getRole();
        if(role == null || role == "") newUser.setRole("student");

        User user = modelMapper.map(newUser, User.class);
        User createdUser = repository.saveAndFlush(user);
        UserDto userDto =  modelMapper.map(createdUser, UserDto.class);
        return userDto;
    }

    //Edit
    public UserDto update(PatchUserDto updateUser, Integer id, BindingResult result) throws MethodArgumentNotValidException{
        String role = updateUser.getRole();
        if(role == "") updateUser.setRole("student");
        if(repository.existsByNameAndIdNot(updateUser.getName(), id)){
            result.addError(nameErrorObj);
        }
        if(repository.existsByEmailAndIdNot(updateUser.getEmail(), id)){
            result.addError(emailErrorObj);
        }
        if (result.hasErrors()) throw new MethodArgumentNotValidException(null, result);
        User user = mapUser(getById(id), updateUser);
        User updatedUser = repository.saveAndFlush(user);
        UserDto userDto =  modelMapper.map(updatedUser, UserDto.class);
        return userDto;
    }

    //Match Argon Password
    public User match(MatchUserDto matchUser) throws PasswordException {
        User user;

        if(repository.existsByEmail(matchUser.getEmail())){
            user = repository.findByEmail(matchUser.getEmail().trim()); //Get user มาจาก Database ตาม email ที่ส่งมา
        } else {
            throw new ApiNotFoundException("Email does not exist");
        }
        String userArgon2Password = user.getPassword(); //เอา Argon2 password มาจาก Database
        if(argon2.verify(userArgon2Password, matchUser.getPassword())){ //Match raw password จาก DTO ว่าหากเปลี่ยนเป็น Argon2 แล้วจะ == Argon2 ใน Database มั้ย
            System.out.println("MATCHED");
        } else {
            throw new PasswordException();
        }
        return user;
    }



    //sha256
    private String sha256(final String base) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    private User mapUser(User existingUser, PatchUserDto updateUser) {
        if(updateUser.getName() != null && !updateUser.getName().trim().equals(""))
            existingUser.setName(updateUser.getName().trim());
        if(updateUser.getEmail() != null && !updateUser.getEmail().trim().equals(""))
            existingUser.setEmail(updateUser.getEmail().trim());
        if(updateUser.getRole() != null && !updateUser.getRole().equals(""))
            existingUser.setRole(UserRole.valueOf(updateUser.getRole()));
        return existingUser;
    }
}

//    //Match
//    public User match(MatchUserDto matchUser) throws PasswordException {
//        User user = new User();
//
//        if(repository.existsByEmail(matchUser.getEmail())){
//            user = repository.findByEmail(matchUser.getEmail().trim()); //Get user มาจาก Database ตาม email ที่ส่งมา
//        } else {
//            throw new ApiNotFoundException("Email does not exist");
//        }
//        String userShaPassword = user.getPassword(); //เอา Sha password มาจาก Database
//        if(sha256(matchUser.getPassword()).equals(userShaPassword)){ //Match raw password จาก DTO ว่าหากเปลี่ยนเป็น sha แล้วจะ == sha ใน Database มั้ย
//            System.out.println("MATCHED");
//        } else {
//            throw new PasswordException();
//        }
//        return user;
//    }
