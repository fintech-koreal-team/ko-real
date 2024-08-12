import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';

const CurrencyConverter = ({ amount, fromCurrency, toCurrency }) => {
  const [convertedAmount, setConvertedAmount] = useState(amount);
  const [exchangeRates, setExchangeRates] = useState({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // API 호출을 위한 함수
  const fetchExchangeRate = useCallback(async (fromCurrency, toCurrency) => {
    if (!fromCurrency || !toCurrency || fromCurrency === toCurrency) {
      return 1; // 같은 통화일 때 환율은 1로 설정
    }

    setLoading(true);
    setError(null);
    try {
      const response = await axios.get('http://localhost:8080/currency/rate', {
        params: {
          fromCurrency,
          toCurrency
        },
      });

      if (response.status === 200) {
        // API 응답에서 환율 정보 추출
        const rate = response.data.data;
        // 상태 업데이트
        setExchangeRates(prevRates => ({
          ...prevRates,
          [`${fromCurrency}-${toCurrency}`]: rate
        }));
        return rate;
      } else {
        // 비정상적인 응답 처리
        throw new Error(`Unexpected response status: ${response.status}`);
      }
    } catch (error) {
      console.error('Error fetching exchange rate:', error);
      setError('Failed to fetch exchange rate. Please try again later.');
      return null;
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    if (!amount || !fromCurrency || !toCurrency || fromCurrency === toCurrency) {
      setConvertedAmount(amount); // 같은 통화일 경우 원본 금액을 그대로 사용
      return;
    }

    const fetchData = async () => {
      const rateKey = `${fromCurrency}-${toCurrency}`;
      let rate = exchangeRates[rateKey]; // 변경 가능하도록 let으로 선언

      if (rate === undefined) {
        console.log("Rate not found in cache, fetching from API...");
        rate = await fetchExchangeRate(fromCurrency, toCurrency);
      }

      console.log("Fetched Rate:", rate);
      
      if (rate !== null && amount !== null) {
        const converted = amount * rate;
        setConvertedAmount(converted);
      }
    };

    fetchData();
  }, [amount, fromCurrency, toCurrency, exchangeRates, fetchExchangeRate]);

  if (loading) {
    return <span>Loading...</span>;
  }

  if (error) {
    return <span>{error}</span>;
  }

  return (
    <span>
      {toCurrency} {Number(convertedAmount).toFixed(2)}
    </span>
  );
};

export default CurrencyConverter;
