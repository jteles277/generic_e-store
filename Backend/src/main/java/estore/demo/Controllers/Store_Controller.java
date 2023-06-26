package estore.demo.Controllers;

// Spring
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import estore.demo.Models.User;
import estore.demo.Services.Auth_Service; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;  

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/estore")
public class Store_Controller {

        @Autowired
        Auth_Service auth_service;  

        @PostMapping("/login")
        public ResponseEntity<?> loginUser(@RequestBody User user) { 

                User db_user = auth_service.login(user);
                
                if(db_user != null){
                        return ResponseEntity.ok(db_user);
                }else{
                        return ResponseEntity.badRequest().body("Error: Account not found!");
                } 
        }

        @PostMapping("/register")
        public ResponseEntity<?> registerUser(@RequestBody User user) { 

                User db_user = auth_service.register(user);

                if(db_user != null){
                        return ResponseEntity.ok(db_user);
                }else{
                        return ResponseEntity.badRequest().body("Error: Email is already in use!");
                } 

        }
 
}
