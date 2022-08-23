package sit.int221.oasipserver.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import sit.int221.oasipserver.dtos.user.CreateUserDto;
import sit.int221.oasipserver.dtos.user.UserDto;
import sit.int221.oasipserver.entities.User;
import sit.int221.oasipserver.exception.type.ApiNotFoundException;
import sit.int221.oasipserver.repo.UserRepository;
import sit.int221.oasipserver.utils.RoleValidate;
import sit.int221.oasipserver.utils.ListMapper;

import java.util.List;

@Service
public class UserService {
    @Autowired private UserRepository repository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private ListMapper listMapper;
    @Autowired private RoleValidate roleValidate;

    //nameError
    final private FieldError nameErrorObj = new FieldError("createUserDto",
            "name", "Name already exist");
    //emailError
    final private FieldError emailErrorObj = new FieldError("createUserDto",
            "email", "Email already exist");
    //roleError
    final private FieldError roleErrorObj = new FieldError("createUserDto",
            "role", "Role must be 'student' or 'admin'");

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
    public UserDto create(CreateUserDto newUser, BindingResult result) throws MethodArgumentNotValidException {
        newUser.setName(newUser.getName().trim());
        User user = modelMapper.map(newUser, User.class);
        if (roleValidate.roleCheck(user)) result.addError(roleErrorObj);
        if (repository.existsByName(newUser.getName())) result.addError(nameErrorObj);
        if (repository.existsByEmail(newUser.getEmail())) result.addError(emailErrorObj);
        if (result.hasErrors()) throw new MethodArgumentNotValidException(null, result);
        return modelMapper.map(repository.saveAndFlush(user), UserDto.class);
    }

    //Edit
    public UserDto update(CreateUserDto updateUser, Integer id, BindingResult result) throws MethodArgumentNotValidException{
        User user = mapUser(getById(id), updateUser);
        if(repository.existsByNameAndIdNot(updateUser.getName(), id)){
            result.addError(nameErrorObj);
        }

        if(repository.existsByEmailAndIdNot(updateUser.getEmail(), id)){
            result.addError(emailErrorObj);
        }

        if (result.hasErrors()) throw new MethodArgumentNotValidException(null, result);
        return modelMapper.map(repository.saveAndFlush(user), UserDto.class);
    }


    private User mapUser(User existingUser, CreateUserDto updateUser) {
        if(updateUser.getName() != null)
            existingUser.setName(updateUser.getName().trim());
        if(updateUser.getEmail() != null)
            existingUser.setEmail(updateUser.getEmail().trim());
        if(updateUser.getRole() != null)
//            existingUser.setRole(String.valueOf(updateUser.getRole()).trim());
            existingUser.setRole(updateUser.getRole());
        return existingUser;
    }
}
