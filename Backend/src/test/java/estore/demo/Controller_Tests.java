package estore.demo;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import estore.demo.Models.Users;
import estore.demo.Services.Auth_Service;  
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;  

@WebMvcTest(estore.demo.Controllers.Store_Controller.class)
class Controller_Tests {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private Auth_Service auth_service;

	@Test
    void Login_Success_Test() throws Exception {

        // Mock the behavior of the auth_service.login() method
        Users mockUser = new Users("test@example.com", "password");
        Mockito.when(auth_service.login(Mockito.any(Users.class))).thenReturn(mockUser);

        // Create a test User object
        Users testUser = new Users("test@example.com", "password");

        // Send a POST request to "/estore/login" with the test User object as the request body
        mockMvc.perform(MockMvcRequestBuilders.post("/estore/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(mockUser)));
    }

    @Test
    void Login_Error_Test() throws Exception {

        // Mock the behavior of the auth_service.login() method to return null
        Mockito.when(auth_service.login(Mockito.any(Users.class))).thenReturn(null);

        // Create a test User object
        Users testUser = new Users("test@example.com", "password");

        // Send a POST request to "/estore/login" with the test User object as the request body
        mockMvc.perform(MockMvcRequestBuilders.post("/estore/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Error: Account not found!"));
    }

    @Test
    void Register_Success_Test() throws Exception {

        // Mock the behavior of the auth_service.register() method
        Users mockUser = new Users("test@example.com", "password");
        Mockito.when(auth_service.register(Mockito.any(Users.class))).thenReturn(mockUser);

        // Create a test User object
        Users testUser = new Users("test@example.com", "password");

        // Send a POST request to "/estore/register" with the test User object as the request body
        mockMvc.perform(MockMvcRequestBuilders.post("/estore/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(mockUser)));
    }

    @Test
    void Register_Error_Test() throws Exception {
		
        // Mock the behavior of the auth_service.register() method to return null
        Mockito.when(auth_service.register(Mockito.any(Users.class))).thenReturn(null);

        // Create a test User object
        Users testUser = new Users("test@example.com", "password");

        // Send a POST request to "/estore/register" with the test User object as the request body
        mockMvc.perform(MockMvcRequestBuilders.post("/estore/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(testUser)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("Error: Email is already in use!"));
    }  
	
	// Utility method to convert an object to its JSON representation
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    } 
}
