import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Table from "react-bootstrap/Table";
import Card from "react-bootstrap/Card";
import axios from "axios";
import Navbar from "react-bootstrap/Navbar";
import Button from "react-bootstrap/Button";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Nav from "react-bootstrap/Nav";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form"; 
import LogoutButton from "./LogoutButton";
import { useNavigate } from 'react-router-dom';


function Order(order){ 
 
  
  const [item, setItem] = useState([]); 

  const storedUser = sessionStorage.getItem("user");
  const user = storedUser ? JSON.parse(storedUser) : null;  
    
  useEffect(() => {
    console.log(order)
      if (!!user) {
          fetch_Item(); 
      }
    }, []);

  const fetch_Item = () => {
    axios
      .get(`http://localhost:6868/estore/get_item?item_id=` +  order.order.item_id)
      .then((response) => {
          setItem(response.data);
      })
      .catch((error) => {
        console.error(error);
    }); 
  }
  
    
  
    return ( 

      <Card style={{ width: '18rem', margin:"2%" }}>
          <Card.Img variant="top" src={item.image_url} />
          <Card.Body>
          <Card.Title>{item.name}</Card.Title>
          <Card.Text>
              {item.description}
          </Card.Text>
              <Card.Text>status: <t style={{color:"orange"}}>{order.order.status}</t></Card.Text> 
          </Card.Body>
      </Card>  
    );
       
}
export default Order;