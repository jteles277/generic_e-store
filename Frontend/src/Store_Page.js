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
        console.log(item)
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
  
    
    const colCount = 4;
    let rowCount = Math.floor(items.length / colCount) + 1

    //Index is needed to keep track of the current element that we are one.
    let index = 0

    //This is the driver function for building the grid system.
    const buildGrid = () => {
        return (
            renderRows()
        )
    }

    //Returns For example, we can have a row with 2 columns inside it.
    const renderRows = () => {
        let rows = []
        
        for(let row = 0; row < rowCount; row++) {
            rows.push(
                <Row className='Row'>
                    {
                        renderCols()
                    }
                </Row>
            )
        }
        
        return rows
    }

    //Returns an array of columns with the children inside.
    const renderCols = () => {
        let cols = []
        
        //If you want to add more bootstrap breakpoints you can pass them as props here.
        for(let col = 0; col < colCount; col++) {
            if(index < items.length) {
                cols.push(
                    <Col className='Col'>
                        <Card style={{ width: '18rem', margin:"2%" }}>
                            <Card.Img variant="top" src={items[index].image_url} />
                            <Card.Body>
                            <Card.Title>{items[index].name}</Card.Title>
                            <Card.Text>
                                {items[index].description}
                            </Card.Text>
                                <Card.Text style={{color:"green"}}>{items[index].price} $</Card.Text>
                                <Button variant="secondary" style={{backgroundColor:"Purple", borderColor:"Purple"}} onClick={() => buy_item(items[index])}>Buy</Button>
                            </Card.Body>
                        </Card> 
                    </Col>
                )
                index++
            }
        }
        
        return cols
    }
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

        <h2 style={{margin:"3%",alignItems: "center", justifyContent: "center"}}> Store Catalog</h2>
        <Container> 
   
             
   
            <Row xs={1} md={4}>
        
    
                <div style={{margin:"5%",alignItems: "center", justifyContent: "center"}}>{items.map((item) => (
            
                    <Col> 
                    
                        <Card key={item.id} style={{ width: '18rem', margin:"2%", marginRight:"1px" }}>
                            <Card.Img variant="top" src={item.image_url} />
                            <Card.Body>
                                <Card.Title>{item.name}</Card.Title>
                                <Card.Text>
                                    {item.description}
                                </Card.Text>
                                <Card.Text style={{color:"green"}}>{item.price} $</Card.Text>
                                <Button variant="secondary" style={{backgroundColor:"Purple", borderColor:"Purple"}} onClick={() => buy_item(item)}>Buy</Button>
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