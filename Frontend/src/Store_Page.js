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

function Store_Page() {
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
        .get(`http://localhost:6868/estore/get_catalog`)
        .then((response) => {
          setItems(response.data);
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
    
    const order_item = () => {
        console.log(sitem.id); 
        console.log(user.id); 
        console.log(point); 
        const order = {
            item_id: sitem.id,
            user_id: user.id,
            pickup_id: point
        };
        
        axios.post(`http://localhost:6868/estore/place_order`,order).then(response => { 
            console.log(response.data); 
        });
         
        handleClose();
    };
  
    
  
    return (
      <>

        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
            <Modal.Title>{sitem.name}</Modal.Title>
            </Modal.Header>
            <Modal.Body>{sitem.description}</Modal.Body>
            <Modal.Footer>
                <Form.Select onChange={e => setPoint(e.target.value)} aria-label="Default select example">
                    <option>Choose Pickup Point</option>
                    {points.map((pointt) => (
                        <option value={pointt.id} >{pointt.name} ({pointt.location} )</option>
                    ))} 
                </Form.Select>
            <Button variant="primary" onClick={order_item} >
                Pay {sitem.price}$
            </Button>
            </Modal.Footer>
        </Modal>

        <Navbar bg="light" expand="lg">
          <Container>
            <Navbar.Brand href="/store">E Store </Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
              <Nav className="me-auto">
                <Nav.Link href="/store">Home</Nav.Link> 
                <Nav.Link href="/my-items">My Orders</Nav.Link> 
                <LogoutButton/>
              </Nav>
            </Navbar.Collapse>
          </Container>
        </Navbar> 
        <h2 style={{margin:"3%",alignItems: "center", justifyContent: "center"}}> Store Catalog</h2>
        <Container>
            <Row>
        
    
                <div style={{margin:"5%",alignItems: "center", justifyContent: "center"}}>{items.map((item) => (
            
                    <Col  > 
                    
                        <Card style={{ width: '18rem', margin:"2%" }}>
                            <Card.Img variant="top" src={item.image_url} />
                            <Card.Body>
                            <Card.Title>{item.name}</Card.Title>
                            <Card.Text>
                                {item.description}
                            </Card.Text>
                                <Card.Text style={{color:"green"}}>{item.price} $</Card.Text>
                                <Button variant="secondary" style={{backgroundColor:"green"}} onClick={() => buy_item(item)}>Buy</Button>
                            </Card.Body>
                        </Card> 
                    </Col>
               
                 ))}
                </div>
      
            </Row>
        </Container>
      </> 
    );
  }
  
  export default Store_Page;