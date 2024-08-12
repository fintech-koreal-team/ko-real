import React, { useEffect, useState } from "react";
import "./CSS/Home.css";
import { Link } from "react-router-dom";
import axios from "axios";
import CurrencyConverter from "../Components/CurrencyConverter/CurrencyConverter";

// Set the default base URL for all axios requests
const api = axios.create({
  baseURL: 'http://localhost:8080', // Your base URL
  timeout: 1000, // Optional: Set a timeout for requests
});

const Home = ({ currency }) => {
  const [brands, setBrands] = useState([]);

  const [newArrivals, setNewArrivals] = useState([]);

  const [bestSellers, setBestSellers] = useState([]);

  useEffect(() => {
  // Fetch data for brands
  api.get('/products/top-brand')  // API endpoint for top brands
  .then(response => {
    const brands = response.data.data.map(brand => ({
      id: brand.id,
      name: brand.name,
      imgUrl: brand.imgUrl,
    }));
    setBrands(brands);
  })
  .catch(error => console.error('Error fetching brands:', error));

  // Fetch data for new arrivals
  api.get('/products/top-newest')  // API endpoint for new arrivals
  .then(response => {
    const products = response.data.data.map(product => ({
      id: product.id,
      name: product.name,
      imgUrl: product.imgUrl,
      originalPrice: product.price,
      discountedPrice: product.discountedPrice
    }));
    setNewArrivals(products);
  })
  .catch(error => console.error('Error fetching new arrivals:', error));

  // Fetch data for best sellers
  api.get('/products/top-popular')  // API endpoint for best sellers
  .then(response => {
    const products = response.data.data.map(product => ({
      id: product.id,
      name: product.name,
      imgUrl: product.imgUrl,
      originalPrice: product.price,
      discountedPrice: product.discountedPrice
    }));
    setBestSellers(products);
  })
  .catch(error => console.error('Error fetching best sellers:', error));
  }, []);

  return (
    <div className="depth-frame">

      <Section title="Featured Brands">
        <div className="brand-container">
          {brands.map((brand) => (
            <BrandCard key={brand.id} brand={brand} />
          ))}
        </div>
      </Section>

      <Section title="New Arrivals">
        <div className="product-container">
          {newArrivals.map((product) => (
            <ProductCard key={product.id} product={product} currency={currency}/>
          ))}
        </div>
      </Section>

      <Section title="Best Sellers">
        <div className="product-container">
          {bestSellers.map((product) => (
            <ProductCard key={product.id} product={product} currency={currency} />
          ))}
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

const BrandCard = ({ brand }) => (
  <Link to={`/brand/${brand.id}`} className="brand-card">
    <div className="brand-image" style={{ backgroundImage: `url(${brand.imgUrl})` }}/>
    <div className="brand-name">{brand.name}</div>
  </Link>
);

const ProductCard = ({ product, currency }) => (
  <Link to={`/productpage/${product.id}`} className="product-card">
    <div className="product-header">
    <img src={product.imgUrl} alt={product.name} />
    </div>
    <div className="product-details">
      <div className="product-name">{product.name}</div>
      <div className="product-description">{product.description}</div>
      <div className="product-price">
        <span className="original-price">
        
          <CurrencyConverter 
            amount={product.originalPrice} 
            fromCurrency="USD"  // Default currency is USD
            toCurrency={currency} // CurrencyButton에서 선택된 통화 
          />
        </span>
        <span className="discounted-price">
          
          <CurrencyConverter 
            amount={product.discountedPrice} 
            fromCurrency="USD"  // Default currency is USD
            toCurrency={currency} 
          />
        </span>
      </div>
    </div>
  </Link>
);

export default Home;
