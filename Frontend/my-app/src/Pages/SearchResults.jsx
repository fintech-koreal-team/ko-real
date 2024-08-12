import React from 'react';
import { useLocation } from 'react-router-dom';
import CurrencyConverter from '../Components/CurrencyConverter/CurrencyConverter';
import './CSS/SearchResults.css'; // CSS 파일 경로

const SearchResults = ({ currency }) => {
  const location = useLocation();
  const { results = [] } = location.state || {}; // 검색 결과 받아오기

  return (
    <div className='depth-frame'>
      <div className="search-results-container">
        <h2>Search Results</h2>
        <div className="search-results-product-list">
          {results.length > 0 ? (
            results.map(product => (
              <div key={product.id} className="search-results-product-card">
                <img src={product.imgUrl} alt={product.name} className="search-results-product-image" />
                <h2 className="search-results-product-name">{product.name}</h2>
                <p className="search-results-product-price">
                  <span className="search-results-original-price">
                    <CurrencyConverter 
                      amount={product.price} 
                      fromCurrency="USD" 
                      toCurrency={currency} 
                    />
                  </span>
                  <span className="search-results-discounted-price">
                    <CurrencyConverter 
                      amount={product.discountedPrice} 
                      fromCurrency="USD" 
                      toCurrency={currency} 
                    />
                  </span>
                </p>
              </div>
            ))
          ) : (
            <p>No products found.</p>
          )}
        </div>
      </div>  
    </div>
  );
};

export default SearchResults;
