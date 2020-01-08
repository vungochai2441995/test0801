package net.example.servicetest;


import net.example.dao.UsersDAO;
import net.example.dto.UserDTO;
import net.example.entity.User;
import net.example.model.request.CreateUsersRequest;
import net.example.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserserviceUnitTest {
    private User user;
    private UserDTO userDTO;
    private CreateUsersRequest createUsersRequest;

    @InjectMocks
    private UsersService usersService;

    @Mock
    private UsersDAO usersDAO;

@BeforeEach
    void initUserservice(){
    MockitoAnnotations.initMocks(this);
    userDTO = new UserDTO();
    userDTO.setUsername("usernameDTOTest");
    userDTO.setEmail("emailDTOTest@gmail.com");
    userDTO.setAddress("addressDTOTest");

    createUsersRequest = new CreateUsersRequest();
    createUsersRequest.setUsername("createUsersRequestTest");
    createUsersRequest.setEmail("createUsersRequest@gmail.com");
    createUsersRequest.setAddress("createUsersRequest");

    user = new User();
    user.setUsername("usernameEntityTest");
    user.setEmail("emailEntitytest@gmail.com");
    user.setAddress("addressEntityTest");
}
    @Test
    void testGetAllUsers(){
        List<User> userList = new CopyOnWriteArrayList<>();
        userList.add(user);
        when(usersDAO.findAll()).thenReturn(userList);
        List<UserDTO> userDTOList = usersService.getAllUsers();
        assertNotNull(userDTOList);
        assertEquals(userDTOList.get(0).getUsername(),user.getUsername());
        assertEquals(userDTOList.get(0).getAddress(),user.getAddress());
        assertEquals(userDTOList.get(0).getEmail(),user.getEmail());
    }

    @Test
    void testFindUserByUsername(){
        when(usersDAO.findByUsername(anyString())).thenReturn(user);
        UserDTO m_userDTO = usersService.findUserByUsername(anyString());
        assertNotNull(m_userDTO);
        assertEquals(user.getUsername(),m_userDTO.getUsername());
        assertEquals(user.getAddress(),m_userDTO.getAddress());
        assertEquals(user.getEmail(),m_userDTO.getEmail());
    }

    @Test
    void testFindUserByEmail(){
        when(usersDAO.findByEmail(anyString())).thenReturn(user);
        UserDTO m_userDTO = usersService.findUserByEmail(anyString());
        assertNotNull(m_userDTO);
        assertEquals(user.getUsername(),m_userDTO.getUsername());
        assertEquals(user.getAddress(),m_userDTO.getAddress());
        assertEquals(user.getEmail(),m_userDTO.getEmail());
    }

    @Test
    void testCreateUser_FailCreateWhenUsernameExist(){
        when(usersDAO.findByUsername(createUsersRequest.getUsername())).thenReturn(user);
        assertNotNull(user);
        assertEquals(usersService.createUser(createUsersRequest),0);
    }

    @Test
    void testCreateUser_CorrectCreateWhenUsernameNotExist(){
        when(usersDAO.findByUsername(createUsersRequest.getUsername())).thenReturn(null);
        assertEquals(usersService.createUser(createUsersRequest),1);
    }

    @Test
    void testDeleteUser_FailDeleteWhenUsernameNotExist(){
        when(usersDAO.findByUsername(anyString())).thenReturn(null);
        assertEquals(usersService.deleteUserByUsername(anyString()),0);
    }

    @Test
    void testDeleteUser_CorrectDeleteWhenUsernameExist(){
        when(usersDAO.findByUsername(anyString())).thenReturn(user);
        assertNotNull(user);
        assertEquals(usersService.deleteUserByUsername(anyString()),1);
    }

    @Test
    void testUpdateUser_FailUpdateWhenUsernameNotExist(){
        when(usersDAO.findByUsername(createUsersRequest.getUsername())).thenReturn(null);
        assertEquals(usersService.updateUser(createUsersRequest),0);
    }

    @Test
    void testUpdateUser_CorrectUpdateWhenUsernameExist(){
        when(usersDAO.findByUsername(createUsersRequest.getUsername())).thenReturn(user);
        assertNotNull(user);
        assertEquals(usersService.deleteUserByUsername(anyString()),0);
    }

}
