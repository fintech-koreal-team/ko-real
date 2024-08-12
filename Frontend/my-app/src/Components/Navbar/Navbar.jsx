import React, { useState } from 'react';
import './Navbar.css';
import { Link } from 'react-router-dom';
import SearchBar from '../SearchBar/SearchBar'; // 새로운 SearchBar 컴포넌트 가져오기
import LoginButton from '../LoginButton/LoginButton'; // 새로운 AuthButtons 컴포넌트 가져오기
import CurrencyButton from '../CurrencyButton/CurrencyButton';
import cartimg from '../../assets/cart.png'

const Navbar = ({ onCurrencyChange }) => {
  const [menu, setMenu] = useState("shop");
  const [user, setUser] = useState({
    name: "Rose",
    profilePicture: "/path-to-profile-picture.jpg" // Add the correct path to the user's profile picture
  });

  const handleSignOut = () => {
    // Implement sign-out logic here
    setUser(null);
  };

  return (
    <div className='nav'>
      <div className='nav-title'>
        <Link to='/' className='nav-logo'>
          <button>KO:REAL</button>
        </Link>
      </div>
      <ul className="nav-menu">
        <li onClick={() => { setMenu("skincare") }}><Link to='/skincare'>Skincare</Link></li>
        <li onClick={() => { setMenu("shop") }}><Link to='/shop'>Makeup</Link></li>
        <li onClick={() => { setMenu("shop") }}><Link to='/shop'>Hair & Body</Link></li>
        <li onClick={() => { setMenu("shop") }}><Link to='/shop'>Tools & Brushes</Link></li>
        <li>
          <SearchBar /> {/* SearchBar 컴포넌트 사용 */}
        </li>
      </ul>

      <ul className="nav-login-cart">
        <li className='currency-select'>
          <div className="currency-selector">
            <CurrencyButton onCurrencyChange={onCurrencyChange} />
          </div>
        </li>
        <li  className='nav-cart'>
          <Link to="/cart">
            <button>
              <img src={cartimg} alt='cart' className='nav-cart-img' />
              Cart
            </button>
          </Link>
        </li>
        <li className='nav-login'>
          <LoginButton user={user} onSignOut={handleSignOut} />
        </li>
      </ul>
    </div>
  );
};

export default Navbar;


