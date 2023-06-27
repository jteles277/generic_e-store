package estore.demo.API;

import java.util.List;

import org.springframework.stereotype.Service;

import estore.demo.Models.Order;
import estore.demo.Models.PickUpPoint;

@Service
public class Picky_API_Access {
    

    //private String host = "http://localhost:6869/picky"; 
    
    public String get_points(){

        //String url = host + "/get_points";

        String a= "[{\"id\":\"1\",\"name\": \"Padaria do seu Zé\",\"location\": \"rua do passal, Recardaes, Agueda, Aveiro\"},{\"id\":\"2\",\"name\": \"Papelaria Almeida\",\"location\": \"rua do pardal, Aveiro\"},{\"id\":\"3\",\"name\": \"CTT Center\",\"location\": \"rua de Mordor, Porto\"},{\"id\":\"4\",\"name\": \"Boca de fumo 2B\",\"location\": \"avenida de Hogwarts, Viseu\"}]";
        
        return a;
    }

    public String place_order(Order order){

        //String url = host + "/place_order";

        return "0";
        
    }

    public String check_status(){

        //String url = host + "/check_status";

        return "{\"point\":{\"name\": \"Papelaria Almeida\",\"location\": \"rua do pardal, Aveiro\"}, \"status\":\"On the way\"}";
        
    }

}
