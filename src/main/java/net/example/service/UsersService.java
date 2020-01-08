package net.example.service;

import net.example.dao.UsersDAO;
import net.example.dto.UserDTO;
import net.example.entity.User;
import net.example.model.request.CreateUsersRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class UsersService implements IUsersService {
    @Autowired
    private UsersDAO usersDAO;

    public List<UserDTO> getAllUsers() {
        List<User> users = usersDAO.findAll();
        List<UserDTO> userDTOS = new CopyOnWriteArrayList<>();
        for (User user:users)
        {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user,userDTO);
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    public int createUser(CreateUsersRequest createUsersRequest){
        User users = usersDAO.findByUsername(createUsersRequest.getUsername());
        if (users != null){
            return 0;
        }else {
            users = new User();
            users.setUsername(createUsersRequest.getUsername());
            users.setAddress(createUsersRequest.getAddress());
            users.setEmail(createUsersRequest.getEmail());
            usersDAO.save(users);
            return 1;
        }
    }

    public UserDTO findUserByUsername(String u){
        User user = usersDAO.findByUsername(u);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        return userDTO;
    }
    public UserDTO findUserByEmail(String e){
        User user = usersDAO.findByEmail(e);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        return userDTO;
    }

    public int deleteUserByUsername(String u){
        User users = usersDAO.findByUsername(u);
        if(users != null){
            usersDAO.deleteById(users.getId());
            return 1;
        }else {
            return 0;
        }
    }

    public int updateUser(CreateUsersRequest createUsersRequest){
        User users = usersDAO.findByUsername(createUsersRequest.getUsername());
        if (users == null){
            return 0;
        }else {
            users.setAddress(createUsersRequest.getAddress());
            users.setEmail(createUsersRequest.getEmail());
            usersDAO.save(users);
            return 1;
        }
    }
}
