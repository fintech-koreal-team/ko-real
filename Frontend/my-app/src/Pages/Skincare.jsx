import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from "react-router-dom";
import './CSS/Skincare.css'; // Import the CSS file
import CurrencyConverter from '../Components/CurrencyConverter/CurrencyConverter';

const Skincare = ({ currency }) => {
  const [products, setProducts] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchSkincareProducts = async () => {
      try {
        const response = await axios.get('http://localhost:8080/products/category', {
          params: { category: 'SKINCARE' }
        });
        if (response.data.data && response.data.data.content) {
          setProducts(response.data.data.content); 
        } else {
          setProducts([]);
        }
      } catch (err) {
        setError('Failed to fetch skincare products.');
      }
    };

    fetchSkincareProducts();
  }, []);

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <div className="depth-frame">
      <Section title="Skincare Products">
        <div className="product-container">
          {products.length > 0 ? (
            products.map((product) => (
              <ProductCard key={product.id} product={product} currency={currency}/>
            ))
          ) : (
            <p>No skincare products found.</p>
          )}
        </div>
      </Section>
    </div>
  );
};

const Section = ({ title, children }) => (
  <div className="section">
    <div className="section-header">{title}</div>
    {children}
  </div>
);

const ProductCard = ({ product, currency }) => (
  <Link to={`/productpage/${product.id}`} className="product-card">
    <div className="product-header">
      <img src={product.imgUrl} alt={product.name} />
    </div>
    <div className="product-details">
      <div className="product-name">{product.name}</div>
      <div className="product-price">
        <span className="original-price">
          <CurrencyConverter 
            amount={product.price} 
            fromCurrency="USD"  
            toCurrency={currency} 
          />
        </span>
        <span className="discounted-price">
          <CurrencyConverter 
            amount={product.discountedPrice} 
            fromCurrency="USD"  
            toCurrency={currency} 
          />
        </span>
      </div>
    </div>
  </Link>
);

export default Skincare;

