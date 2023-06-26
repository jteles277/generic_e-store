package estore.demo.Services;

// Spring Annotations
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;  

 

 
// Project Imports 
import estore.demo.Models.User;
import estore.demo.Rep.User_Repository;

@Service
public class Auth_Service {
 
     
    @Autowired
    User_Repository userRepository;
     
    
    public User login(User user) { 
        
        // Check DB for user 
        User old_user = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()) == null){
            return null;
        }
        System.out.println(old_user);
        
        return old_user;
    }

    public User register(User user){

         
        if (userRepository.existsByEmail(user.getEmail())) {
            return null;
        } 

        userRepository.save(user);  
        return user;
    }
 
}
