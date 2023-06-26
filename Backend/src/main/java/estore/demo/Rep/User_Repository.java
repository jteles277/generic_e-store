package estore.demo.Rep;
 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import estore.demo.Models.Users;

@Repository
public interface User_Repository extends JpaRepository<Users, Long> {

    Boolean existsByEmail(String email);
    Users findByEmailAndPassword(String email, String password);
}
