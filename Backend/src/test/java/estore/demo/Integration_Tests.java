package estore.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate; 
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import estore.demo.Models.User;
import estore.demo.Rep.User_Repository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class Integration_Tests {

    @Autowired
    private User_Repository userRepository;
 
    private String port = "8080";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void login_Success_Test() {
        // Create a test User object
        User user = new User("test@example.com", "password");
        userRepository.save(user);

        // Send a POST request to "/estore/login" with the test User object as the request body
        ResponseEntity<User> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/estore/login",
                new HttpEntity<>(user),
                User.class
        );

        // Assert the response status code and the returned User object
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(user);
    }

    @Test
    void login_Error_Test() {
        // Create a test User object
        User user = new User("test@example.com", "password");

        // Send a POST request to "/estore/login" with the test User object as the request body
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/estore/login",
                HttpMethod.POST,
                new HttpEntity<>(user),
                String.class
        );

        // Assert the response status code and the error message
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isEqualTo("Error: Account not found!");
    }

    @Test
    void register_Success_Test() {
        // Create a test User object
        User user = new User("test@example.com", "password");

        // Send a POST request to "/estore/register" with the test User object as the request body
        ResponseEntity<User> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/estore/register",
                new HttpEntity<>(user),
                User.class
        );

        // Assert the response status code and the returned User object
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(user);
    }

    @Test
    void register_Error_Test() {
        // Create a test User object
        User user = new User("test@example.com", "password");
        userRepository.save(user);

        // Send a POST request to "/estore/register" with the test User object as the request body
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/estore/register",
                HttpMethod.POST,
                new HttpEntity<>(user),
                String.class
        );

        // Assert the response status code and the error message
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isEqualTo("Error: Email is already in use!");
    }
}