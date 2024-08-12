import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./CSS/Checkout.css";
import ShippingAddress from "../Components/ShippingAddress/ShippingAddress";
import PaymentMethod from "../Components/PaymentMethod/PaymentMethod";
import ContactInformation from "../Components/ContactInfo/ContactInfo";

const Checkout = () => {
  const [cartItems, setCartItems] = useState([]);
  const [orderDetails, setOrderDetails] = useState({
    name: "",
    address: "",
    email: "",
    paymentMethod: "remittance_transfer",
  });
  const navigate = useNavigate();

  useEffect(() => {
    const savedCartItems = JSON.parse(localStorage.getItem("cart")) || [];
    setCartItems(savedCartItems);
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setOrderDetails({ ...orderDetails, [name]: value });
  };

  const handlePlaceOrder = () => {
    const totalAmount = calculateTotal(); // Get the total price
    alert("Order placed successfully!\nProceeding to...");
    localStorage.removeItem("cart"); // 장바구니 비우기
    navigate("/remittance-transfer", { state: { totalAmount } }); // Pass the total amount to the next page
  };

  const calculateTotal = () => {
    return cartItems.reduce((total, item) => {
      const price = item.price || 0;
      const quantity = item.quantity || 0;
      return total + price * quantity;
    }, 0).toFixed(2);
  };

  return (
    <div className="checkout-container">
      <div className="checkout-summary">
        <div className="section-title">Order Summary</div>
        {cartItems.length === 0 ? (
          <p>Your cart is empty.</p>
        ) : (
          cartItems.map((item, index) => (
            <div className="order-item" key={index}>
              <img src={item.imgUrl} alt="Product" />
              <div className="product-details">
                <div className="product-price">${(item.price || 0).toFixed(2)}</div>
                <div className="product-description">
                  {item.name || "제품명(brand_name)"}, {item.option || "option"}, {item.quantity || 0} quantity
                </div>
              </div>
            </div>
          ))
        )}
        <div className="order-summary">
          <div className="summary-item">
            <div className="summary-label">Subtotal</div>
            <div className="summary-value">${calculateTotal()}</div>
          </div>
          <div className="summary-item">
            <div className="summary-label">Shipping</div>
            <div className="summary-value">Free</div>
          </div>
          <div className="summary-item">
            <div className="summary-label">Total</div>
            <div className="summary-value">${(parseFloat(calculateTotal())).toFixed(2)}</div>
          </div>
        </div>
      </div>
      <div className="checkout-form-container">
        <ShippingAddress />
        < ContactInformation email={orderDetails.email} onChange={handleInputChange} />
        <PaymentMethod
          paymentMethod={orderDetails.paymentMethod}
          onChange={handleInputChange}
        />
        <button type="button" onClick={handlePlaceOrder} className="checkout-button">
          Checkout
        </button>
      </div>
    </div>
  );
};

export default Checkout;
