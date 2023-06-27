import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './index.css'; 
import reportWebVitals from './reportWebVitals';
import { UserProvider } from './UserContext';

import Main from './Main';  
import Register from './Register';  
import Store_Page from './Store_Page';  
import MyItems_Page from './MyItems_Page';  

ReactDOM.render(
  <BrowserRouter>
  <UserProvider>
  <Routes>

  <Route path='' element={<Main/>} /> 
  <Route path='/*' element={<Main/>} />
  <Route path='/register' element={<Register/>} />
  <Route path='/store' element={<Store_Page/>} /> 
  <Route path='/my-items' element={<MyItems_Page/>} />  
  </Routes>
  </UserProvider>
  </BrowserRouter>
  
  ,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
