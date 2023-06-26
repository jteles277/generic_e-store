package estore.demo.Services;

import java.util.List;
import java.util.Optional;

// Spring Annotations
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;   
 
// Project Imports  
import estore.demo.Models.Items;
import estore.demo.Rep.Item_Repository; 

@Service
public class Store_Service {
     
    @Autowired
    Item_Repository item_repository; 

    public List<Items> get_catalog() { 
         
        // Returns a list with all Items in the catalog
        return item_repository.findAll();
    }

    public Items get_item(long item_id){
         
        // Returns a list with all Items in the catalog
        Optional<Items> item = item_repository.findById(item_id);
        if(!item.isPresent()){
            return null;
        } 
        
        return item.get();
    }

    public Items add_item(Items item){ 
        
        if (item_repository.existsByName(item.getName())) {
            return null;
        } 

        item_repository.save(item);
        
        return item;
    }
 
}
