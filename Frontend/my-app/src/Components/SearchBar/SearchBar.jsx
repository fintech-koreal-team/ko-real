import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const SearchBar = () => {
  const [searchQuery, setSearchQuery] = useState(''); // 검색어 상태
  const navigate = useNavigate();

  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value); // 입력된 검색어를 상태에 저장
  };

  const handleSearchSubmit = async (e) => {
    if (e.key === 'Enter') { // Enter 키를 눌렀을 때 검색을 실행
      e.preventDefault();
      try {

        console.log('검색어:', searchQuery);

        const response = await axios.get(`http://localhost:8080/products/search`, {
          params: {
            name: searchQuery,
            description: searchQuery,
            page: 0,
            size: 10
          }
        });

        console.log('서버 응답:', response);

        // 검색 결과 페이지로 이동
        navigate(`/search`, { 
          state: { results: response.data.data.data.content } 
        });
      } catch (error) {
        console.error('검색 중 오류 발생:', error);
      }
    }
  };

  return (
    <input 
      type="text" 
      placeholder="Search" 
      className="nav-search" 
      value={searchQuery} // 입력된 검색어를 상태에 반영
      onChange={handleSearchChange} // 입력된 검색어가 변경될 때 상태를 업데이트
      onKeyDown={handleSearchSubmit} // Enter 키를 눌렀을 때 검색 실행
    />
  );
};

export default SearchBar;
