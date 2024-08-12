import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import CurrencyConverter from "../Components/CurrencyConverter/CurrencyConverter";
import axios from "axios";
import "./CSS/ProductPage.css";

const formatDate = (dateString) => {
  const date = new Date(dateString);
  
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  
  return `${year}-${month}-${day}`;
};


const ProductPage = ( {currency} ) => {
  const { productId } = useParams(); // URL에서 productId를 가져옴
  const navigate = useNavigate();

  const [product, setProduct] = useState();
  const [quantity, setQuantity] = useState(1);
  const [activeTab, setActiveTab] = useState("description");
  const [reviews, setReviews] = useState([]); // Add state for reviews

  useEffect(() => {
    axios
      .get(`http://localhost:8080/products/${productId}`)
      .then((response) => {
        console.log("Response data:", response.data);
        setProduct(response.data.data); // 백엔드에서 받은 상품 데이터를 상태에 저장
      })
      .catch((error) => console.error("상품 데이터를 가져오는 중 오류 발생:", error));
  }, [productId]);

  useEffect(() => {
    if (activeTab === "reviews") {
      // Fetch reviews data when the Reviews tab is active
      const fetchReviews = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/reviews/${productId}`);
          console.log("Entire response data:", response.data); // 전체 응답 데이터 확인
          setReviews(response.data.data); // Assuming response data contains a data array
        } catch (error) {
          console.error("Error fetching reviews:", error);
        }
      };
      fetchReviews();
    }
  }, [activeTab, productId]);

  if (!product) {
    return <div>로딩 중...</div>;
  }

  const calculateDiscountPercentage = () => {
    const originalPrice = product.price;
    const discountedPrice = product.discountedPrice;

    if (originalPrice > 0) {
      return Math.round(((originalPrice - discountedPrice) / originalPrice) * 100);
    }
    return 0;
  };

  const decreaseQuantity = () => {
    if (quantity > 1) {
      setQuantity(quantity - 1);
    }
  };

  const increaseQuantity = () => {
    setQuantity(quantity + 1);
  };

  const handleAddToCart = () => {
    const newCartItem = {
      id: product.id,
      name: product.name,
      imgUrl: product.imgUrl,
      price: product.discountedPrice,
      quantity: quantity,
    };

    const existingCartItems = JSON.parse(localStorage.getItem("cart")) || []; // 기존 장바구니 항목 가져오기
    const existingItemIndex = existingCartItems.findIndex(
      (item) => item.id === product.id
    );

    if (existingItemIndex >= 0) {
      // 장바구니에 이미 해당 상품이 있을 경우 수량을 업데이트
      existingCartItems[existingItemIndex].quantity += quantity;
    } else {
      // 장바구니에 새 상품을 추가
      existingCartItems.push(newCartItem);
    }

    localStorage.setItem("cart", JSON.stringify(existingCartItems)); // 장바구니를 로컬 스토리지에 저장
    alert("상품이 장바구니에 추가되었습니다!");
    navigate("/"); // 장바구니 페이지로 리디렉션
  };

  return (
    <div className="product-page">
      <div className="product-header">
        <img src={product.imgUrl} alt={product.name} />

        <div className="product-info">
          <h1>{product.name}</h1>
          <p className="brand-name">{product.brandName}</p>
          <div className="price-section">

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

            <span className="discount-tag">
              {calculateDiscountPercentage()}% Off
            </span>
          </div>
          <div className="rating-section">
            <span className="stars">★★★★☆</span>
            {/* <span className="reviews-count">{product.reviews.length} 리뷰</span> */}
          </div>
          <div className="quantity-section">
            <button onClick={decreaseQuantity}>-</button>
            <input type="number" value={quantity} readOnly />
            <button onClick={increaseQuantity}>+</button>
          </div>
          <button onClick={handleAddToCart} className="add-to-cart">
            Add to Cart
          </button>
        </div>
      </div>

      <div className="tabs">
        <button onClick={() => setActiveTab("description")} className={activeTab === "description" ? "active" : ""}>Description</button>
        <button onClick={() => setActiveTab("ingredients")} className={activeTab === "ingredients" ? "active" : ""}>Ingredients</button>
        <button onClick={() => setActiveTab("reviews")} className={activeTab === "reviews" ? "active" : ""}>Reviews</button>
      </div>

      <div className="tab-content">                                                 
        {activeTab === "description" && (
          <div className="product-description">
            <h3>Description</h3>
            <p>{product.description}</p>
          </div>
        )}
        {activeTab === "ingredients" && (
          <div className="product-ingredients">
            <h3>Ingredients</h3>
            <p>{product.ingredients}</p>
          </div>
        )}
        {activeTab === "reviews" && (
          <div className="product-reviews">
            <h3>Reviews</h3>
            {reviews.length > 0 ? (
              reviews.map((review, index) => (
                <div key={index} className="review">
                  <div className="review-header">
                    <div>
                      <strong>{review.member.username}</strong><br/>
                      <span className="review-date">{formatDate(review.createdAt)}</span>
                    </div>
                  </div>
                  <div className="review-rating">
                    {'⭐'.repeat(review.rating)}
                  </div>
                  <p>{review.comment}</p>
                  <div className="review-actions">
                    <span>👍 {review.likes}</span>
                    <span>👎 {review.dislikes}</span>
                  </div>
                </div>
              ))
            ) : (
              <p>No reviews available.</p>
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default ProductPage;
