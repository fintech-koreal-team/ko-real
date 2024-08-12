import React from "react";
import "./Footer.css";

const Footer = () => {
  return (
    <footer className="footer">
      <div className="footer-links">
        <div className="footer-link">Privacy Policy</div>
        <div className="footer-link">Terms of Service</div>
        <div className="footer-link">Contact Us</div>
      </div>
      <div className="footer-copy">
        <span>© 2024 Naturie Cosmetics</span>
      </div>
    </footer>
  );
};

export default Footer;
