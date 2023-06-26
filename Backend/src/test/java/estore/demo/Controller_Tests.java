package estore.demo;

// Test
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

// Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import estore.demo.Models.Items;
// Project
import estore.demo.Models.Users;
import estore.demo.Services.Auth_Service;  
import estore.demo.Services.Store_Service;  


import com.fasterxml.jackson.databind.ObjectMapper;  

@WebMvcTest(estore.demo.Controllers.Store_Controller.class)
class Controller_Tests {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private Auth_Service auth_service;

    @MockBean
    private Store_Service store_service;

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

    @Test
    void getItem_Success_Test() throws Exception {
        Long itemId = 1L;
        Items item = new Items("Test Item", "Description", "10.99", "http://example.com/image.jpg");

        Mockito.when(store_service.get_item(itemId)).thenReturn(item);

        mockMvc.perform(MockMvcRequestBuilders.get("/estore/get_item")
                .param("item_id", itemId.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk()) 
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Item"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("10.99"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image_url").value("http://example.com/image.jpg"));
    }

    @Test
    void getItem_ItemNotFound_Test() throws Exception {
        Long itemId = 1L;

        Mockito.when(store_service.get_item(itemId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/estore/get_item")
                .param("item_id", itemId.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Error: Item not found!"));
    }

    @Test
    void getCatalog_Success_Test() throws Exception {
        List<Items> items = new ArrayList<>();
        items.add(new Items("Item 1", "Description 1", "9.99", "http://example.com/item1.jpg"));
        items.add(new Items("Item 2", "Description 2", "19.99", "http://example.com/item2.jpg"));

        Mockito.when(store_service.get_catalog()).thenReturn(items);

        mockMvc.perform(MockMvcRequestBuilders.get("/estore/get_catalog"))
                .andExpect(MockMvcResultMatchers.status().isOk()) 
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Item 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Description 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value("9.99"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].image_url").value("http://example.com/item1.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Item 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("Description 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price").value("19.99"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].image_url").value("http://example.com/item2.jpg"));
    }

    @Test
    void getCatalog_EmptyCatalog_Test() throws Exception {
        List<Items> items = new ArrayList<>();

        Mockito.when(store_service.get_catalog()).thenReturn(items);

        mockMvc.perform(MockMvcRequestBuilders.get("/estore/get_catalog"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Error: Empty Catalog!"));
    }

    @Test
    void addNewItem_Success_Test() throws Exception {
        Items item = new Items("New Item", "Description", "14.99", "http://example.com/new_item.jpg");

        Mockito.when(store_service.add_item(Mockito.any(Items.class))).thenReturn(item);

        mockMvc.perform(MockMvcRequestBuilders.post("/estore/add_item")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"New Item\", \"description\": \"Description\", \"price\": \"14.99\", \"image_url\": \"http://example.com/new_item.jpg\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk()) 
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Item"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("14.99"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image_url").value("http://example.com/new_item.jpg"));
    }

    @Test
    void addNewItem_ItemAlreadyExists_Test() throws Exception {
        Mockito.when(store_service.add_item(Mockito.any(Items.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/estore/add_item")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"New Item\", \"description\": \"Description\", \"price\": \"14.99\", \"image_url\": \"http://example.com/new_item.jpg\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Error: Item Already Exists!"));
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
