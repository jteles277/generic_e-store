package estore.demo.Models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; 

@Entity
@Table(name = "estore_orders", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Order implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @NotNull 
    private Long item_id;
 
    private Long api_id;

    @NotNull
    private Long userId;

    @NotNull
    private Long pickup_id;
 
    private String status;


    public Long getPickup_id() {
        return pickup_id;
    }


    public void setPickup_id(Long pickup_id) {
        this.pickup_id = pickup_id;
    }


    public Order() {
        // Empty constructor
    }


    public Order(Long item_id, Long api_id, Long user_id, Long pickup_id) {
        this.item_id = item_id;
        this.api_id = api_id;
        this.userId = user_id;
        this.pickup_id = pickup_id;
    }

    public Order(Long item_id, Long user_id, Long pickup_id) {
        this.item_id = item_id;
        this.userId = user_id;
        this.pickup_id = pickup_id;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public Long getId() {
        return id;
    } 

    public Long getItem_id() {
        return item_id;
    }


    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }


    public Long getApi_id() {
        return api_id;
    }


    public void setApi_id(Long api_id) {
        this.api_id = api_id;
    }


    public Long getUser_id() {
        return userId;
    }


    public void setUser_id(Long user_id) {
        this.userId = user_id;
    }


    @Override
    public String toString() {
        return "Order [id=" + id + ", item_id=" + item_id + ", api_id=" + api_id + ", userId=" + userId + ", pickup_id="
                + pickup_id + "]";
    } 
      

}