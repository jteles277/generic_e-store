import 'bootstrap/dist/css/bootstrap.min.css';
import React, { useState } from 'react';
import axios from 'axios';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import { useContext } from 'react';
import UserContext from './UserContext';
import { useNavigate } from 'react-router-dom';

function Main() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const { updateUser } = useContext(UserContext);
    const navigate = useNavigate();
  
    const handleSubmit = (event) => {

      if (email!="" && password!=""){
        event.preventDefault();
        const data = { email: email, password: password };


        axios.post('http://localhost:6868/estore/login', data)
          .then(response => {
            setEmail('');
            setPassword('');
            console.log(response.data);
            if (response.data != "Error: Account not found!"){
              updateUser(response.data);
              sessionStorage.setItem('user', JSON.stringify(response.data));
              navigate('/store')
            }else{
              alert("Error: Account not found!")
            }
          })
          .catch(error => {
            alert("Error: Account not found!");
          });
      }
    };
  
    return (
      <>
        <Container className='d-flex flex-column align-items-center justify-content-center text-center ' style={{marginTop:"10%"}}>
          <h1 style={{marginBottom:"2%"}}>E<t style={{color:"Purple"}}>.</t>Store Login</h1>
          <Form onSubmit={handleSubmit}>
            <Form.Group className="mb-3" controlId="formBasicEmail">
              <Form.Label>Email address</Form.Label>
              <Form.Control type="email" placeholder="Enter email" value={email} onChange={(e) => setEmail(e.target.value)} />
              <Form.Text className="text-muted"> 
              </Form.Text>
            </Form.Group>
  
            <Form.Group className="mb-3" controlId="formBasicPassword">
              <Form.Label>Password</Form.Label>
              <Form.Control type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
            </Form.Group>
  
            <Button style={{marginBottom:"2%", backgroundColor:"Purple", borderColor:"Purple" }} variant="primary" type="submit" >
              Log in
            </Button>
          </Form> 
            <a href="/register" style={{textDecoration: "none", color: "Purple",  textDecoration: "underline"}}>
              Register
            </a>
          
        </Container>
      </>
    );
  }
  
  export default Main;