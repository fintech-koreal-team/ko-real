import React from "react";
import "./ContactInfo.css";

const ContactInformation = ({ email, onChange }) => {
  return (
    <div className="contact-information">
      <div className="section-title">Contact Information</div>
      <div className="form-group">
        <input
          type="email"
          name="email"
          value={email}
          onChange={onChange}
          placeholder="asdf@naver.com"
          className="input-field info-mail"
        />
        <span className="email-notice">
          You will receive a confirmation email at this email address.
        </span>
      </div>
    </div>
  );
};

export default ContactInformation;
