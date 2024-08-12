import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./CSS/Cart.css";

const Cart = () => {
  const [cartItems, setCartItems] = useState([]);
  const navigate = useNavigate(); 

  useEffect(() => {
    // Load cart items from local storage or API
    const savedCartItems = JSON.parse(localStorage.getItem("cart")) || [];
    setCartItems(savedCartItems);
  }, []);

  const handleQuantityChange = (index, amount) => {
    const updatedItems = [...cartItems];
    updatedItems[index].quantity += amount;

    if (updatedItems[index].quantity <= 0) {
      updatedItems.splice(index, 1);
    }

    setCartItems(updatedItems);
    localStorage.setItem("cart", JSON.stringify(updatedItems));
  };

  const calculateTotal = () => {
    return cartItems.reduce((total, item) => total + item.price * item.quantity, 0).toFixed(2);
  };

  const handleCheckout = () => {
    navigate("/checkout");
  };

  return (
    <div className="cart-container">
      <h1 className="cart-title">Shopping Cart</h1>

      {cartItems.length === 0 ? (
        <p className="empty-cart">Your cart is empty.</p>
      ) : (
        <div className="cart-items">
          {cartItems.map((item, index) => (
            <div className="cart-item" key={index}>
              <img src={item.imgUrl} alt={item.name} className="cart-item-image" />
              <div className="cart-item-details">
                <h3>{item.name}</h3>
                <p>
                  Color
                </p>
              </div>
              <div className="cart-item-quantity">
                <button onClick={() => handleQuantityChange(index, -1)}>-</button>
                <span>{item.quantity}</span>
                <button onClick={() => handleQuantityChange(index, 1)}>+</button>
              </div>
              <div className="cart-item-price">${(item.price * item.quantity).toFixed(2)}</div>
            </div>
          ))}

          <div className="cart-summary">
            <p><span className="cart-summary-label">Subtotal</span> <span>${calculateTotal()}</span></p>
            <p><span className="cart-summary-label">Shipping</span> <span>Free</span></p>
            <p><span className="cart-summary-label">Total</span> <span>${calculateTotal()}</span></p>
          </div>

          <button className="checkout-button" onClick={handleCheckout}>
            Checkout
          </button>

  
          <Link to="/" className="view-new-arrivals">
          View All New Arrivals
          </Link>


        </div>
      )
    }
    </div>
  );
}
export default Cart;


