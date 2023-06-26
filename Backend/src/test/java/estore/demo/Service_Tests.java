package estore.demo;

import estore.demo.Models.Users;
import estore.demo.Rep.User_Repository;
import estore.demo.Services.Auth_Service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mock.Strictness;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
 
@ExtendWith(MockitoExtension.class) 
class Service_Tests {

    @Mock(strictness = Strictness.LENIENT)
    private User_Repository userRepository;

    @InjectMocks
    private Auth_Service authService; 

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
}