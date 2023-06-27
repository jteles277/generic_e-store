package estore.demo;

import estore.demo.API.Picky_API_Access;
import estore.demo.Models.Items;
import estore.demo.Models.Order;
import estore.demo.Models.PickUpPoint;
import estore.demo.Models.Users;
import estore.demo.Rep.Item_Repository;
import estore.demo.Rep.Order_Repository;
import estore.demo.Rep.User_Repository;
import estore.demo.Services.Auth_Service;
import estore.demo.Services.Store_Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mock.Strictness;
import org.mockito.junit.jupiter.MockitoExtension; 

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
@ExtendWith(MockitoExtension.class) 
class Service_Tests {

    @Mock(strictness = Strictness.LENIENT)
    private User_Repository userRepository;

    @InjectMocks
    private Auth_Service authService; 

    @Mock(strictness = Strictness.LENIENT)
    private Item_Repository itemRepository;
    @Mock(strictness = Strictness.LENIENT)
    private Order_Repository orderRepository;
    @Mock(strictness = Strictness.LENIENT)
    private Picky_API_Access apiService;

    @InjectMocks
    private Store_Service storeService; 

    @Test
    void login_ValidUser_ReturnsUser() { 

        Users user = new Users("test@example.com", "password");
        when(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(user);

        // Act
        Users result = authService.login(user);

        // Assert
        assertThat(result).isEqualTo(user);
        verify(userRepository, times(1)).findByEmailAndPassword(user.getEmail(), user.getPassword());
    }

    @Test
    void login_InvalidUser_ReturnsNull() { 

        Users user = new Users("test@example.com", "password");
        when(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(null);

        // Act
        Users result = authService.login(user);

        // Assert
        assertThat(result).isNull();
        verify(userRepository, times(1)).findByEmailAndPassword(user.getEmail(), user.getPassword());
    }

    @Test
    void register_ValidUser_ReturnsUser() { 

        Users user = new Users("test@example.com", "password");
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        // Act
        Users result = authService.register(user);

        // Assert
        assertThat(result).isEqualTo(user);
        verify(userRepository, times(1)).existsByEmail(user.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void register_ExistingUser_ReturnsNull() { 

        Users user = new Users("test@example.com", "password");
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        // Act
        Users result = authService.register(user);

        // Assert
        assertThat(result).isNull();
        verify(userRepository, times(1)).existsByEmail(user.getEmail());
        verify(userRepository, never()).save(user);
    }

    @Test
    void getCatalog_Success_Test() {
        // Mock the item repository to return a list of items
        List<Items> items = new ArrayList<>();
        items.add(new Items("Item 1", "Description 1", "9.99", "http://example.com/item1.jpg"));
        items.add(new Items("Item 2", "Description 2", "19.99", "http://example.com/item2.jpg"));
        when(itemRepository.findAll()).thenReturn(items);

        // Call the service method
        List<Items> result = storeService.get_catalog();

        // Verify the repository method was called and the result is correct
        verify(itemRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals("Item 1", result.get(0).getName());
        assertEquals("Item 2", result.get(1).getName());
    }

    @Test
    void getItem_ItemExists_Test() {
        Long itemId = 1L;
        Items item = new Items("Test Item", "Description", "10.99", "http://example.com/image.jpg");
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        Items result = storeService.get_item(itemId);

        verify(itemRepository, times(1)).findById(itemId);
        assertEquals(item, result);
    }

    @Test
    void getItem_ItemNotFound_Test() {
        Long itemId = 1L;
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        Items result = storeService.get_item(itemId);

        verify(itemRepository, times(1)).findById(itemId);
        assertEquals(null, result);
    }

    @Test
    void addNewItem_Success_Test() {
        Items item = new Items("New Item", "Description", "14.99", "http://example.com/new_item.jpg");
        when(itemRepository.existsByName(item.getName())).thenReturn(false);
        when(itemRepository.save(item)).thenReturn(item);

        Items result = storeService.add_item(item);

        verify(itemRepository, times(1)).existsByName(item.getName());
        verify(itemRepository, times(1)).save(item);
        assertEquals(item, result);
    }

    @Test
    void addNewItem_ItemAlreadyExists_Test() {
        Items item = new Items("Existing Item", "Description", "14.99", "http://example.com/existing_item.jpg");
        when(itemRepository.existsByName(item.getName())).thenReturn(true);

        Items result = storeService.add_item(item);

        verify(itemRepository, times(1)).existsByName(item.getName());
        verify(itemRepository, never()).save(any());
        assertEquals(null, result);
    }

    @Test
    void getPoints_Success_Test() {

        // Mock dependencies  
        String response = "[{\"id\":\"1\",\"name\":\"Point A\",\"location\":\"Location A\"},{\"id\":\"2\",\"name\":\"Point B\",\"location\":\"Location B\"}]";
        when(apiService.get_points()).thenReturn(response);

        // Perform the test
        List<PickUpPoint> expectedPoints = new ArrayList<>();
        expectedPoints.add(new PickUpPoint("1", "Point A", "Location A"));
        expectedPoints.add(new PickUpPoint("2", "Point B", "Location B"));
        List<PickUpPoint> actualPoints = storeService.get_points();

        // Verify the results
        assertNotNull(actualPoints);
        assertEquals(expectedPoints.size(), actualPoints.size());
        for (int i = 0; i < expectedPoints.size(); i++) {
            assertEquals(expectedPoints.get(i).getName(), actualPoints.get(i).getName());
        }

        // Verify the dependency method was called
        verify(apiService, times(1)).get_points();
    }

    @Test
    void getPoints_EmptyList_Test() {
        // Mock dependencies 
        String response = "[]";
        when(apiService.get_points()).thenReturn(response);

        // Perform the test
        List<PickUpPoint> actualPoints = storeService.get_points();

        // Verify the result
        assertNull(actualPoints);

        // Verify the dependency method was called
        verify(apiService, times(1)).get_points();
    }

    @Test
    void placeOrder_Success_Test() {
        // Mock dependencies 
        Order order = new Order(1L, 2L, 3L, 4L);
        String apiResponse = "5678";
        when(apiService.place_order(order)).thenReturn(apiResponse);
        when(orderRepository.save(order)).thenReturn(order);

        // Perform the test
        Order actualOrder = storeService.place_order(order);

        // Verify the result
        assertNotNull(actualOrder);
        assertEquals(apiResponse, actualOrder.getApi_id().toString());

        // Verify the dependency methods were called
        verify(apiService, times(1)).place_order(order);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void placeOrder_Error_Test() {
        // Mock dependencies 
        Order order = new Order(1L, 2L, 3L, 4L);
        when(apiService.place_order(order)).thenReturn(null);

        // Perform the test
        Order actualOrder = storeService.place_order(order);

        // Verify the result
        assertNull(actualOrder);

        // Verify the dependency method was called
        verify(apiService, times(1)).place_order(order);
        verify(orderRepository, never()).save(Mockito.any());
    }
}