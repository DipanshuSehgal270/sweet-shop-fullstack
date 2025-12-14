import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom'; // Import Link
import { loginUser } from '../services/authService';
import { toast } from 'react-toastify';

const LoginPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const token = await loginUser({ username, password });
      if (token) {
        toast.success("Login Successful!");
        navigate('/'); 
      }
    } catch (error) {
      toast.error("Invalid Username or Password");
    }
  };


  return (
    <div className="container d-flex flex-column justify-content-center align-items-center" style={{ minHeight: '80vh' }}>
      
      <div className="text-center mb-4">
        <h1 className="h3 mb-2">Welcome Back</h1>
        <p className="text-secondary">Enter your details to access your account</p>
      </div>

      <div className="card border-0" style={{ width: '100%', maxWidth: '400px' }}>
        <div className="card shadow border-0">
            <div className="card-header bg-danger text-white text-center py-3"> {/* Changed to Red for Bikaner vibe */}
              <h3 className="mb-0">Bikaner Sweets Login</h3>
            </div>
          <form onSubmit={handleLogin}>
            
            <div className="mb-3">
              <label className="form-label small text-secondary fw-bold">USERNAME</label>
              <input 
                type="text" 
                className="form-control" 
                placeholder="e.g. dipanshu"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required 
              />
            </div>
            
            <div className="mb-4">
              <label className="form-label small text-secondary fw-bold">PASSWORD</label>
              <input 
                type="password" 
                className="form-control" 
                placeholder="••••••••"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required 
              />
            </div>

            <button type="submit" className="btn btn-primary w-100 py-2 mb-3">
              Sign In
            </button>
          </form>

          <div className="text-center">
            <span className="text-secondary small">Don't have an account? </span>
            <Link to="/register" className="text-decoration-none fw-bold" style={{ color: 'var(--text-primary)' }}>
              Sign up
            </Link>
          </div>

        </div>
      </div>
    </div>
  );
};

export default LoginPage;