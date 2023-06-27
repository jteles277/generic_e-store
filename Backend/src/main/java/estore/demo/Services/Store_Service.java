package estore.demo.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Spring Annotations
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import estore.demo.API.Picky_API_Access;
// Project Imports  
import estore.demo.Models.Items;
import estore.demo.Models.Order;
import estore.demo.Models.PickUpPoint; 
import estore.demo.Rep.Item_Repository;
import estore.demo.Rep.Order_Repository;

import org.json.JSONArray;
import org.json.JSONObject;  

@Service
public class Store_Service {
     
    @Autowired
    Item_Repository item_repository; 

    @Autowired
    Order_Repository order_repository; 

    @Autowired
    Picky_API_Access api_service; 

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
    
    public List<PickUpPoint> get_points() { 
        
        List<PickUpPoint> points = new ArrayList<PickUpPoint>();

        // Returns a list with all PickUp Points
        String response = api_service.get_points(); 

        // get parts from response till reaching the address
        JSONArray obj = (JSONArray) new JSONArray(response); 

        if (obj.length() == 0) {
            return null;
        } 

        for (int i = 0; i < obj.length(); i++) {

            JSONObject list = (JSONObject) obj.get(i);  

            String id =  (String) list.get("id");
            String name =  (String) list.get("name");
            String location =  (String) list.get("location"); 
            points.add(new PickUpPoint(id, name, location));
        } 


        return points;
    }

    public Order place_order(Order order) { 
    
        // Send to external API
        String response = api_service.place_order(order);

        if (response == null) {
            return null;
        } 

        // Save new order + the api id 
        order.setApi_id(Long.parseLong(response));  

        order_repository.save(order);

        // return the new order   
        return order;
    }
 
}
