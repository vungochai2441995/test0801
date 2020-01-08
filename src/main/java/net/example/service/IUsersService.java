package net.example.service;

import net.example.dto.UserDTO;
import net.example.entity.User;
import net.example.model.request.CreateUsersRequest;

import java.util.List;

public interface IUsersService {
    public List<UserDTO> getAllUsers();
    public int createUser(CreateUsersRequest createUsersRequest);
    public UserDTO findUserByUsername(String u);
    public UserDTO findUserByEmail(String e);
    public int deleteUserByUsername(String u);
    public int updateUser(CreateUsersRequest createUsersRequest);

}
