import React, { useState } from "react";
import "./ShippingAddress.css";

const ShippingAddress = () => {
    const [orderDetails, setOrderDetails] = useState({
      address: "12345 street, San Francisco, CA 94107",
      email: "asdf@naver.com",
      name: "Alexis Johnson",
    });
    const [isEditingAddress, setIsEditingAddress] = useState(false);
  
    const toggleEditAddress = () => {
      setIsEditingAddress(!isEditingAddress);
    };
  
    const handleInputChange = (e) => {
      const { name, value } = e.target;
      setOrderDetails({ ...orderDetails, [name]: value });
    };
  
    const saveAddress = () => {
      setIsEditingAddress(false);
    };
  
    return (
      <div className="shipping-address-section">
        <div className="section-title">Shipping Address</div>
        {isEditingAddress ? (
          <div className="form-group">
            <input
              type="text"
              name="address"
              value={orderDetails.address}
              onChange={handleInputChange}
              placeholder="Enter your shipping address"
              className="input-field"
            />
            <button onClick={saveAddress} className="save-button">
              Save
            </button>
          </div>
        ) : (
          <div className="shipping-address">
            <div className="contact-address">
              <div className="email">{orderDetails.email}</div>
              <div className="name">{orderDetails.name}</div>
              <div className="street">{orderDetails.address}</div>
            </div>
            <button onClick={toggleEditAddress} className="change-button">
              Change
            </button>
          </div>
        )}
      </div>
    );
  };
  
  export default ShippingAddress;
  