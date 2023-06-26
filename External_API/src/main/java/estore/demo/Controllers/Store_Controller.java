package estore.demo.Controllers;

// Spring
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
 
import estore.demo.Models.PickUpPoint;
 

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;  

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/picky")
public class Store_Controller {
 

        @PostMapping("/place_order")
        public ResponseEntity<?> placeOrder () {  
                
                return ResponseEntity.ok(0);  
        }

        @GetMapping("/check_status")
        public ResponseEntity<?> checkStatus (@RequestParam Long id) { 

              return ResponseEntity.ok("On the way");  
        }

        @GetMapping("/get_points")
        public ResponseEntity<?> getPoints() { 

                List<PickUpPoint> points = new ArrayList<PickUpPoint>();

                points.add(new PickUpPoint("Padaria do seu Zé", "rua do passal, Recardaes, Agueda, Aveiro"));
                points.add(new PickUpPoint("Papelaria Almeida", "rua do pardal, Aveiro"));
                points.add(new PickUpPoint("CTT Center", "rua de Mordor, Porto"));
                points.add(new PickUpPoint("Boca de fumo 2B", "avenida de Hogwarts, Viseu"));
                
                return ResponseEntity.ok(points);
        }
 
}
