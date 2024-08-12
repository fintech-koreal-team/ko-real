import React from 'react';
import './CSS/RemittanceTransfer.css';
import { useLocation, useNavigate } from "react-router-dom";
import CurrencyConverter from "../Components/CurrencyConverter/CurrencyConverter";

const RemittanceTransfer = ({ depositBank, depositAccountNumber, recipient = "Koreal Inc." }) => {
  const location = useLocation();
  const navigate = useNavigate();
  const { totalAmount } = location.state || { totalAmount: "0.00" }; // Default to "0.00" if not provided

  const handleCheckout = () => {
    alert("Transfer Completed!");
    navigate('/'); // Redirect to the home page
  };

  return (
    <div className="remittance-transfer-container">
      <h2>Remittance Transfer</h2>
      <div className="remittance-transfer-row-horizontal">
        <div className="remittance-transfer-item">
          <span className="remittance-transfer-label">Total Deposit Amount($)</span>
          <span className="remittance-transfer-value">USD {totalAmount}</span>
        </div>
        <div className="remittance-transfer-item">
          <span className="remittance-transfer-label">Total Deposit Amount(₩) </span>
          <span className="remittance-transfer-value">
            <CurrencyConverter amount={totalAmount} fromCurrency="USD" toCurrency="KRW" />
          </span>
        </div>
      </div>
      
      <div className="remittance-transfer-row">
        <span className="remittance-transfer-label">Recipient</span>
        <span className="remittance-transfer-gray">{recipient}</span>
      </div>
      <div className="remittance-transfer-row">
        <span className="remittance-transfer-label">Deposit Bank</span>
        <span className="remittance-transfer-gray">Bank of America</span>
      </div>
      <div className="remittance-transfer-row">
        <span className="remittance-transfer-label">Deposit Account Number</span>
        <span className="remittance-transfer-gray">123456789012</span>
      </div>

      {/* Checkout Box */}
      <div className="checkout-box">
        <button className="remittance-transfer-checkout" onClick={handleCheckout}>
          Checkout
        </button>
      </div>
      
      <p className="remittance-transfer-footer">
        Your personal data will be used to process your order, support your experience throughout this website, and for other purposes described in our privacy policy.
      </p>
    </div>
  );
};

export default RemittanceTransfer;
