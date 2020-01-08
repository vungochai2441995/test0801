package net.example.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.example.BtvnApplication;
import net.example.BtvnApplicationTests;
import net.example.dao.UsersDAO;
import net.example.dto.UserDTO;
import net.example.entity.User;
import net.example.model.request.CreateUsersRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.ParseException;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest  {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UsersDAO usersDAO;
    @AfterTestClass
    public void resetDb() {
        usersDAO.deleteAll();
    }

    @Test
    public void findAllUser() throws Exception{
        createTestUsername_ForTestFindUserByUsername("findUserInit1");
        createTestUsername_ForTestFindUserByUsername("findUserInit2");
        mvc.perform( MockMvcRequestBuilders.get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists());
    }

    @Test
        public void createUser_ReturnResult1_IfNoUserNameExist() throws Exception{
        mvc.perform( MockMvcRequestBuilders
                .post("/users")
                .content(asJsonString(new CreateUsersRequest("createUser_ReturnResult1_IfNoUserNameExist","testCreateUser@gmail.com","Ha Noi")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }

    @Test
    public void createUser_ReturnResult0_IfUserNameExist() throws Exception{
        createTestUsername_ForTestFindUserByUsername("createUser_ReturnResult0_IfUserNameExist");
        mvc.perform( MockMvcRequestBuilders
                .post("/users")
                .content(asJsonString(new CreateUsersRequest("createUser_ReturnResult0_IfUserNameExist","testCreateUser@gmail.com","Ha Noi")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(0));
    }

    @Test
    public void deleteUse_ReturnResult0_IfNoUserNameExist() throws Exception{
        mvc.perform( MockMvcRequestBuilders
                .delete("/users?key=deleteUse_ReturnResult0_IfNoUserNameExist")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(0));
    }

    @Test
    public void deleteUse_ReturnResult1_IfUserNameExist() throws Exception{
        createTestUsername_ForTestFindUserByUsername("deleteUse_ReturnResult1_IfUserNameExist");
        mvc.perform( MockMvcRequestBuilders
                .delete("/users?key=deleteUse_ReturnResult1_IfUserNameExist")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));
    }

    @Test
    public void findUserByEmail() throws Exception {
        createTestEmailUser_ForTestFindUserByEmail("testUserEmail@gamil.com");
        mvc.perform(get("/users/email?key=testUserEmail@gamil.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email", is("testUserEmail@gamil.com")));
    }

    @Test
    public void findUserByUsername() throws Exception{
        createTestUsername_ForTestFindUserByUsername("findUserByUsername");
        mvc.perform(get("/users/username?key=findUserByUsername").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username", is("findUserByUsername")));
    }



    @Test
    public void updateUser_ReturnResult0_IfNoUsernameExist() throws Exception{

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("updateUser");
        userDTO.setEmail("updateUser@gmail.com");
        userDTO.setAddress("hanoi");
        mvc.perform( MockMvcRequestBuilders
                .put("/users")
                .content(asJsonString(userDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(0));

    }

    @Test
    public void updateUser_ReturnResult1_IfUsernameExist() throws Exception{

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("updateUser_ReturnResult1_IfUsernameExist");
        userDTO.setEmail("updateUser@gmail.com");
        userDTO.setAddress("hanoi");

        createTestUsername_ForTestFindUserByUsername("updateUser_ReturnResult1_IfUsernameExist");
        mvc.perform( MockMvcRequestBuilders
                .put("/users")
                .content(asJsonString(userDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(1));

    }


    //Generate Demo information for test
    private void createTestEmailUser_ForTestFindUserByEmail(String email) throws ParseException {
        User userEntity = new User();
        userEntity.setEmail(email);
        userEntity.setUsername("testUsernameEmail");
        usersDAO.save(userEntity);
    }

    private void createTestUsername_ForTestFindUserByUsername(String username) throws ParseException {
        User userEntity = new User();
        userEntity.setUsername(username);
        System.out.println(usersDAO.save(userEntity));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
