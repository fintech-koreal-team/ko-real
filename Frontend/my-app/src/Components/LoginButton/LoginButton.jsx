import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import "./LoginButton.css";
import signIcon from '../../assets/signin.png'

const LoginButton = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    // 컴포넌트가 마운트될 때 로그인 상태 확인
    const loggedIn = localStorage.getItem('isLoggedIn');
    setIsLoggedIn(loggedIn === 'true');
  }, []);

  const handleSignOut = () => {
    // 로그아웃 처리
    localStorage.removeItem('access');
    localStorage.removeItem('isLoggedIn');
    setIsLoggedIn(false);
    navigate('/login');
  };

  return (
    <div>
      {isLoggedIn ? (
        <button onClick={handleSignOut} className="sign">
          <img src={signIcon} alt="SignOut" className="login-icon" />
          Sign Out
        </button> // 로그인 상태일 때 Sign Out 버튼
      ) : (
        <Link to="/login">
          <button className="sign">
            <img src={signIcon} alt="SignOut" className="login-icon" />
            Sign In
          </button>
        </Link> // 로그아웃 상태일 때 Sign In 버튼
      )}
    </div>
  );
};

export default LoginButton;
