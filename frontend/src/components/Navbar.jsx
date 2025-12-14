import React from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { logoutUser, getUsername } from '../services/authService';
import { useCart } from '../context/CartContext';

const Navbar = () => {
  const { cart } = useCart();
  const navigate = useNavigate();
  const username = getUsername();

  const handleLogout = () => {
    logoutUser();
    navigate('/login');
  };

  return (
    <nav className="navbar navbar-expand-lg sticky-top mb-5">
      <div className="container">
        <Link className="navbar-brand d-flex align-items-center gap-2" to="/">
          <span style={{ fontSize: '1.2rem' }}>🥨</span> {/* Changed emoji */}
          <span style={{ fontFamily: 'serif', fontWeight: 'bold' }}>Bikaner Sweets</span>
        </Link>
        
        <div className="d-flex align-items-center gap-3">
          <Link to="/cart" className="btn btn-link position-relative text-dark text-decoration-none me-2">
                🛒
                {cart.length > 0 && (
                    <span className="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger" style={{fontSize: '0.6rem'}}>
                        {cart.length}
                    </span>
                )}
            </Link>
          {username ? (
            <>
              <span className="text-secondary small d-none d-md-block">Signed in as <strong>{username}</strong></span>
              
              {/* Optional: Add Admin Links here if needed */}
              
              <button 
                className="btn btn-sm btn-link text-danger text-decoration-none" 
                onClick={handleLogout}
                style={{ fontSize: '0.9rem' }}
              >
                Sign out
              </button>
            </>
          ) : (
            <Link to="/login" className="btn btn-sm btn-primary px-4">Login</Link>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;