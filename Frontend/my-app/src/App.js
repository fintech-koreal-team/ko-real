import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './Components/Navbar/Navbar';
import Home from './Pages/Home';
import SearchResults from './Pages/SearchResults';
import ProductPage from './Pages/ProductPage';
import Skincare from './Pages/Skincare';
import Shop from './Pages/Shop';
import Cart from './Pages/Cart';
import Checkout from "./Pages/Checkout";
import LoginSignup from './Pages/LoginSignup';
import RemittanceTransfer from './Pages/RemittanceTransfer';
import Footer from './Components/Footer/Footer';


function App() {
  const [currency, setCurrency] = useState("USD"); // 기본 통화

  const handleCurrencyChange = (newCurrency) => {
    setCurrency(newCurrency); // 통화 변경 시 상태 업데이트
  };
  const handlePlaceOrder = () => {
    // Logic to handle placing the order goes here.
    // For example, you could navigate to a different page, show a confirmation message, or trigger an API call.
    console.log('Checkout button clicked! Order is being placed...');
  };


  return (
    <Router>
      <Navbar onCurrencyChange={handleCurrencyChange} />
      <Routes>
        <Route path="/" element={<Home currency={currency}/>} />
        <Route path="/search" element={<SearchResults currency={currency}/>} /> 
        <Route path="/productpage/:productId" element={<ProductPage currency={currency}/>} />

        <Route path="/skincare" element={<Skincare currency={currency}/>} />
        <Route path="/shop1" element={<Shop />} />
        <Route path="/shop2" element={<Shop />} />
        

        <Route path="/login" element={<LoginSignup />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/checkout" element={<Checkout />} />
        <Route path="/remittance-transfer" element={<RemittanceTransfer handlePlaceOrder={handlePlaceOrder}/>} />

      </Routes>

      <Footer />
    </Router>
  );
}

export default App;

