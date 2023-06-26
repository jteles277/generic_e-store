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
@Table(name = "estore_users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Users implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
 

    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;

    public Users() {
        // Empty constructor
    }

    public Users(String email, String password) { 
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }  

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", password=" + password + "]";
    }

}