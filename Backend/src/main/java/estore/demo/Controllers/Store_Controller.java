package estore.demo.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estore")
public class Store_Controller {  

    @GetMapping("/check")
        public String Check_API() {
                return "API access is OK!";
        }
}
