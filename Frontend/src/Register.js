import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import axios from 'axios';

function Register() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');

  const handleSubmit = (event) => {

    if (email!="" && password!=""){
      event.preventDefault();
      const data = { email: email, password: password};
      axios.post('http://localhost:6868/estore/register', data)
        .then(response => {
          console.log(response);
          alert("Success");
          setEmail('');
          setPassword('');  
          setName('');
        })
        .catch(error => {
          alert(error);
          });
    }
  };

  return (
    <>
      <Container className='d-flex flex-column align-items-center justify-content-center text-center '>
        <h1>Register</h1>
        <Form onSubmit={handleSubmit}> 
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>Email address</Form.Label>
            <Form.Control type="email" placeholder="Enter email" value={email} onChange={(event) => setEmail(event.target.value)} />
            <Form.Text className="text-muted">
              We'll never share your email with anyone else.
            </Form.Text>
          </Form.Group>

          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control type="password" placeholder="Password" value={password} onChange={(event) => setPassword(event.target.value)} />
          </Form.Group>

          <Button variant="primary" type="submit">
            Submit
          </Button>
        </Form>
        <Button variant="primary" className='mt-3' >
          <a href="/" style={{textDecoration: "none", color: "white"}}>
            Login
          </a>
        </Button>
      </Container>
    </>
  );
  }
  
  export default Register;