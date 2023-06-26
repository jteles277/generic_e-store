package estore.demo.Models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "estore_items", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Items implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String description;

    @NotBlank
    @Size(max = 100)
    private String price;
 
    @Size(max = 1000)
    private String image_url;

    public Items() {
        // Empty constructor
    } 

    public Items(String name,String description, String price, String image_url) {
            
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
    }
    public Long getId() {
        return this.id;
    } 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "Items [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
                + ", image_url=" + image_url + "]";
    } 

}