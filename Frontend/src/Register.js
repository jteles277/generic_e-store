import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Register() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const navigate = useNavigate();

  const handleSubmit = (event) => {

    if (email!="" && password!=""){
      event.preventDefault();
      const data = { email: email, password: password};
      axios.post('http://localhost:6868/estore/register', data)
        .then(response => {
          console.log(response);
          if (response.data != "Error: Email is already in use!"){
            alert("Registered with Success!!");
            setEmail('');
            setPassword('');  
            setName('');
            navigate('/')
          }else
          {alert("Error: Email is already in use!")}
          
        })
        .catch(error => {
          alert("Error: Email is already in use!");
          });
    }
  };

  return (
    <>
      <Container className='d-flex flex-column align-items-center justify-content-center text-center ' style={{marginTop:"10%"}}>
        <h1 style={{marginBottom:"2%"}}>E<t style={{color:"Purple"}}>.</t>Store Register</h1>
        <Form onSubmit={handleSubmit}> 
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>Email address</Form.Label>
            <Form.Control type="email" placeholder="Enter email" value={email} onChange={(event) => setEmail(event.target.value)} />
            <Form.Text className="text-muted"> 
            </Form.Text>
          </Form.Group>

          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control type="password" placeholder="Password" value={password} onChange={(event) => setPassword(event.target.value)} />
          </Form.Group>

          <Button style={{marginBottom:"2%", backgroundColor:"Purple", borderColor:"Purple" }} type="submit">
            Register
          </Button>
        </Form>
         
          <a href="/" style={{textDecoration: "none", color: "Purple",  textDecoration: "underline"}}>
            Login
          </a>
         
      </Container>
    </>
  );
  }
  
  export default Register;