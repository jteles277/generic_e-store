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
            updateUser(response.data);
            sessionStorage.setItem('user', JSON.stringify(response.data));
            navigate('/store')
          })
          .catch(error => {
            alert(error);
          });
      }
    };
  
    return (
      <>
        <Container className='d-flex flex-column align-items-center justify-content-center text-center '>
          <h1>Login</h1>
          <Form onSubmit={handleSubmit}>
            <Form.Group className="mb-3" controlId="formBasicEmail">
              <Form.Label>Email address</Form.Label>
              <Form.Control type="email" placeholder="Enter email" value={email} onChange={(e) => setEmail(e.target.value)} />
              <Form.Text className="text-muted">
                We'll never share your email with anyone else.
              </Form.Text>
            </Form.Group>
  
            <Form.Group className="mb-3" controlId="formBasicPassword">
              <Form.Label>Password</Form.Label>
              <Form.Control type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
            </Form.Group>
  
            <Button variant="primary" type="submit" >
              Submit
            </Button>
          </Form>
          <Button variant="primary" className='mt-3' >
            <a href="/register" style={{textDecoration: "none", color: "white"}}>
              Register
            </a>
          </Button>
        </Container>
      </>
    );
  }
  
  export default Main;