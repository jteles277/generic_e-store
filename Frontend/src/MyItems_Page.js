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
import Order from "./Order";


function MyItems_Page(){ 
 
    const [orders, setOrders] = useState([]);
    const [items, setItems] = useState([]);
    const [points, setPoints] = useState([]);

    const [sitem, setItem] = useState([]);
    const [point, setPoint] = useState([]);
 
    const storedUser = sessionStorage.getItem("user");
    const user = storedUser ? JSON.parse(storedUser) : null; 

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const navigate = useNavigate();
    
    useEffect(() => {
        if (!!user) {
            fetch_Items();
            fetch_Points();
        }
      }, []);
  
    const fetch_Items = () => {
      axios
        .get(`http://localhost:6868/estore/get_all_status?userId=` + user.id)
        .then((response) => {
            setOrders(response.data);
        })
        .catch((error) => {
          console.error(error);
        }); 
 
    };

    const fetch_Points = () => {
        axios
          .get(`http://localhost:6868/estore/get_points`)
          .then((response) => {
            setPoints(response.data);
          })
          .catch((error) => {
            console.error(error);
          });
      };

    const buy_item = (item) => {
         
        setItem(item)
        handleShow();
    }; 
  
    
  
    return (
      <>

       

        <Navbar expand="lg" className="bg-body-tertiary">
        <Container fluid>
        <Navbar.Brand style={{marginLeft:"3%"}}  href="/store">E<t style={{color:"Purple"}}>.</t>Store </Navbar.Brand> 
            <Navbar.Toggle aria-controls="navbarScroll" />
            <Navbar.Collapse id="navbarScroll">
            <Nav
                className="me-auto my-2 my-lg-0"
                style={{ maxHeight: '100px' }}
                navbarScroll
            >
                <Nav.Link href="/store">Home</Nav.Link> 
                <Nav.Link  href="/my-items">My Orders</Nav.Link> 
                 
            </Nav>
            <LogoutButton/> 
            </Navbar.Collapse>
        </Container>
        </Navbar>

        <h2 style={{margin:"3%",alignItems: "center", justifyContent: "center"}}> My Orders</h2>

        <Container>
            <Row>
        
    
                <div style={{margin:"5%",alignItems: "center", justifyContent: "center"}}>{orders.map((order) => (
            
                    <Col>  
                       <Order order={order}></Order>
                    </Col>
               
                 ))}
                </div>
      
            </Row>
        </Container>
      </> 
    );
       
}
export default MyItems_Page;