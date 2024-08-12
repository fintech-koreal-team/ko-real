import React from "react";
import "./PaymentMethod.css";

const PaymentMethod = ({ paymentMethod, onChange }) => {
  return (
    <div className="payment-method-section">
      <div className="section-title">Payment Method</div>
      <div className="payment-method">
        <label>
          <input
            type="radio"
            name="paymentMethod"
            value="card"
            checked={paymentMethod === "card"}
            onChange={onChange}
          />
          Card
        </label>
        <label>
          <input
            type="radio"
            name="paymentMethod"
            value="remittance_transfer"
            checked={paymentMethod === "remittance_transfer"}
            onChange={onChange}
          />
          Remittance Transfer
        </label>
      </div>
    </div>
  );
};

export default PaymentMethod;
