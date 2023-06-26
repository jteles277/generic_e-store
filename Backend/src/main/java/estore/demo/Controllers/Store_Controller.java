package estore.demo.Controllers;

// Spring
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import estore.demo.Models.Items;
import estore.demo.Models.PickUpPoint;
import estore.demo.Models.Users;
import estore.demo.Services.Auth_Service;
import estore.demo.Services.Store_Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;  

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/estore")
public class Store_Controller {

        @Autowired Auth_Service auth_service; 

        @Autowired Store_Service store_service;  

        @PostMapping("/login")
        public ResponseEntity<?> loginUser(@RequestBody Users user) { 

                Users db_user = auth_service.login(user);
                
                if(db_user != null){
                        return ResponseEntity.ok(db_user);
                }else{
                        return ResponseEntity.badRequest().body("Error: Account not found!");
                } 
        }

        @PostMapping("/register")
        public ResponseEntity<?> registerUser(@RequestBody Users user) { 

                Users db_user = auth_service.register(user);

                if(db_user != null){
                        return ResponseEntity.ok(db_user);
                }else{
                        return ResponseEntity.badRequest().body("Error: Email is already in use!");
                } 

        }

        @GetMapping("/get_item")
        public ResponseEntity<?> getItem (@RequestParam Long item_id) { 

                Items item = store_service.get_item(item_id);

                if(item != null){
                        return ResponseEntity.ok(item);
                }else{
                        return ResponseEntity.badRequest().body("Error: Item not found!");
                } 

        }

        @GetMapping("/get_catalog")
        public ResponseEntity<?> getCatalog () { 

                List<Items> items = store_service.get_catalog();
                
                if(items.size() != 0){
                        return ResponseEntity.ok(items);
                }else{
                        return ResponseEntity.badRequest().body("Error: Empty Catalog!");
                } 

        }

        @PostMapping("/add_item")
        public ResponseEntity<?> getCatalog (@RequestBody Items item) { 

                Items item_ = store_service.add_item(item);  

                if(item_ != null){
                        return ResponseEntity.ok(item_);
                }else{
                        return ResponseEntity.badRequest().body("Error: Item Already Exists!");
                }  
               
        }

        @GetMapping("/get_points")
        public ResponseEntity<?> getPoints() { 

                List<PickUpPoint> points = store_service.get_points();
                
                if(points.size() != 0){
                        return ResponseEntity.ok(points);
                }else{
                        return ResponseEntity.badRequest().body("Error: No Points!");
                } 

        }
 
}
