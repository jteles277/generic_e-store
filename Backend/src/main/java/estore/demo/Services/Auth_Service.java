package estore.demo.Services;

// Spring Annotations
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;   
 
// Project Imports 
import estore.demo.Models.Users;
import estore.demo.Rep.User_Repository;

@Service
public class Auth_Service {
     
    @Autowired
    User_Repository userRepository; 

    public Users login(Users user) { 
        
        // Check DB for user 
        Users old_user = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if(old_user == null){
            return null;
        } 
        
        return old_user;
    }

    public Users register(Users user){

         
        if (userRepository.existsByEmail(user.getEmail())) {
            return null;
        } 

        userRepository.save(user);  
        return user;
    }
 
}
