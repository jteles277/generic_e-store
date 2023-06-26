package estore.demo.Rep;
 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import estore.demo.Models.User;

@Repository
public interface User_Repository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}
