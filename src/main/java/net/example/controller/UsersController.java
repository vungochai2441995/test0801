package net.example.controller;

import net.example.dto.UserDTO;
import net.example.entity.User;
import net.example.model.request.CreateUsersRequest;
import net.example.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private IUsersService usersService;

    @GetMapping("")
    public List<UserDTO> test(){
        return usersService.getAllUsers();
    }

    @PostMapping("")
    public int createUser(@RequestBody @Valid CreateUsersRequest createUserRequest) {
        int result = usersService.createUser(createUserRequest);
        return result;
    }

    @PutMapping("")
    public int updateUser(@RequestBody @Valid CreateUsersRequest createUserRequest) {
        int result = usersService.updateUser(createUserRequest);
        return result;
    }

    @GetMapping("/username")
    public UserDTO findUsersByUsername(@RequestParam(value = "key",required = true) String key){
        UserDTO userDTO = usersService.findUserByUsername(key);
        return userDTO;
    }

    @GetMapping("/email")
    public UserDTO findUsersEmail(@RequestParam(value = "key",required = true) String key){
        UserDTO userDTO = usersService.findUserByEmail(key);
        return userDTO;
    }


    @DeleteMapping("")
    public int deleteUsername(@RequestParam(value = "key",required = true) String key){
        return usersService.deleteUserByUsername(key);
    }
}
