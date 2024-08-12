import React, {useState, useEffect} from 'react';
import './CSS/LoginSignup.css';

const getCookie = (name) => {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
};

const LoginSignup = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        // 10분마다 토큰 갱신 확인 (토큰이 만료된 경우 갱신 시도)
        const interval = setInterval(checkAndRefreshToken, 9 * 60 * 1000); // 9분마다 실행
        return () => clearInterval(interval);
    }, []);

    const handleLogin = async (e) => {
        e.preventDefault();

        const formData = new FormData();
        formData.append('email', email);
        formData.append('password', password);

        const response = await fetch('http://localhost:8080/login', {
            method: 'POST',
            body: formData,
            credentials: 'include',
        });

        if (response.ok) {
            const accessToken = getCookie('AccessToken')
            console.log('accessToken', getCookie('AccessToken'));
            console.log('refreshToken', getCookie('RefreshToken'));


            localStorage.setItem('access', accessToken);
            localStorage.setItem('isLoggedIn', true); // 로그인 상태 저장
            alert('Login successful!');
            window.location.href = '/';
        } else {
            alert('Login failed. Please try again.');
            window.location.href = '/login';
        }
    };

    const checkAndRefreshToken = async () => {
        const accessToken = localStorage.getItem('access');
        const refreshToken = getCookie('refresh');

        if (!accessToken || !refreshToken) {
            console.log('Tokens are missing');
            return;
        }

        // 액세스 토큰 만료 여부 확인
        const accessTokenPayload = JSON.parse(atob(accessToken.split('.')[1]));
        const currentTime = Math.floor(Date.now() / 1000);

        if (accessTokenPayload.exp < currentTime) {
            // 액세스 토큰이 만료된 경우, 리프레시 토큰을 사용하여 재발급 요청
            const response = await fetch('http://localhost:8080/refresh-token', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    refreshToken: refreshToken,
                }),
                credentials: 'include', // 쿠키를 포함하여 요청
            });

            if (response.ok) {
                const newAccessToken = response.headers.get('access');
                const newRefreshToken = getCookie('refresh');

                if (newAccessToken) {
                    localStorage.setItem('access', newAccessToken);
                }

                if (newRefreshToken) {
                    // 새로 받은 리프레시 토큰이 있다면 쿠키에 저장
                    document.cookie = `refresh=${newRefreshToken}; path=/;`;
                }
            } else {
                // 재발급 실패 시 처리 (로그아웃, 로그인 페이지로 이동 등)
                alert('Session expired. Please log in again.');
                localStorage.removeItem('isLoggedIn'); // 로그아웃 처리
                window.location.href = '/login';
            }
        }
    };

    return (
        <div className="login-signup-container">
            <h1 className="welcome-title">Welcome!</h1>
            <form className="login-form" onSubmit={handleLogin}>
                <div className="input-group">
                    <input
                        type="email"
                        name="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div className="input-group">
                    <input
                        type="password"
                        name="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                {error && <div className="error-message">{error}</div>}
                <button type="submit" className="login-button">Login</button>
                <button type="button" className="signup-button">Sign Up</button>
                <a href="#" className="forgot-password">Forgot your password?</a>
            </form>
        </div>
    );
};

export default LoginSignup;