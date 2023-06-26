package estore.demo.Rep;
 

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import estore.demo.Models.Items;

@Repository
public interface Item_Repository extends JpaRepository<Items, Long> {
    
    Boolean existsByName(String name);
 
}
