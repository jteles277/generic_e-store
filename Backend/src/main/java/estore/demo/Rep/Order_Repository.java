package estore.demo.Rep;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import estore.demo.Models.Order;
import java.util.List;


@Repository
public interface Order_Repository extends JpaRepository<Order, Long> {
 
    List<Order> findByUserId(Long userId); 
}
