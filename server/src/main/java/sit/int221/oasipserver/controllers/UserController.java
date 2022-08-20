package sit.int221.oasipserver.controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.catalina.authenticator.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sit.int221.oasipserver.dtos.CreateUserDto;
import sit.int221.oasipserver.dtos.Role;
import sit.int221.oasipserver.dtos.UserDetailDto;
import sit.int221.oasipserver.dtos.UserDto;
import sit.int221.oasipserver.services.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired UserService userService;
    @Autowired private ModelMapper modelMapper;

    @GetMapping("")
    public List<UserDto> getAllUser() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserDetailDto getUserById(@PathVariable Integer id) {
        return modelMapper.map(userService.getById(id), UserDetailDto.class);
    }

    @PostMapping("")
    public UserDto createUser(@Valid @RequestBody CreateUserDto newUser, BindingResult result) throws MethodArgumentNotValidException {
        return userService.create(newUser, result);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {userService.delete(id);}

    @PatchMapping("/{id}")
    public UserDto updateUser(
            @Valid @RequestBody CreateUserDto updateUser,
            @PathVariable Integer id,
            BindingResult result) throws MethodArgumentNotValidException {
        return userService.update(updateUser, id, result);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public void handleRole(HttpServletResponse response, InvalidFormatException ex) throws IOException {
        if (ex.getTargetType().isAssignableFrom(Role.class)) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Role must be student or lecturer or admin");
        } else {
            response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        }
    }


}
