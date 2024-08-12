import React, { useState } from 'react';
import { Select, MenuItem } from '@mui/material';
import './CurrencyButton.css';

const CurrencyButton = ({ onCurrencyChange }) => {
  const [currency, setCurrency] = useState('USD');

  const handleCurrencyChange = (event) => {
    const newCurrency = event.target.value;
    setCurrency(newCurrency);
    onCurrencyChange(newCurrency);  // 선택된 통화를 부모 컴포넌트로 전달
  };

  const flags = {
    USD: "https://upload.wikimedia.org/wikipedia/commons/a/a4/Flag_of_the_United_States.svg",
    KRW: "https://upload.wikimedia.org/wikipedia/commons/0/09/Flag_of_South_Korea.svg",
    JPY: "https://upload.wikimedia.org/wikipedia/en/9/9e/Flag_of_Japan.svg",
    EUR: "https://upload.wikimedia.org/wikipedia/commons/b/b7/Flag_of_Europe.svg"
    // 필요에 따라 추가
  };

  return (
    <div className='currency-select'>
      <img
        src={flags[currency]}
        alt={`${currency} Flag`}
        className='flag-icon'
      />
      <Select
        value={currency}
        onChange={handleCurrencyChange}
        displayEmpty
        inputProps={{ 'aria-label': 'Without label' }}
      >
        <MenuItem value="USD">USD</MenuItem>
        <MenuItem value="KRW">KRW</MenuItem>
        <MenuItem value="JPY">JPY</MenuItem>
        <MenuItem value="EUR">EUR</MenuItem>
        {/* 필요에 따라 추가 */}
      </Select>
    </div>
  );
};

export default CurrencyButton;
