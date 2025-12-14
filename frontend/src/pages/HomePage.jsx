import React from 'react';
import { Link } from 'react-router-dom';
import Navbar from '../components/Navbar';

const HomePage = () => {
  return (
    <div style={{ minHeight: '100vh', display: 'flex', flexDirection: 'column' }}>
      <Navbar />
      
      {/* Hero Section */}
      <div className="container flex-grow-1 d-flex align-items-center justify-content-center">
        <div className="row align-items-center w-100">
          
          {/* Left Text */}
          <div className="col-md-6 mb-5 mb-md-0">
            <span className="badge bg-warning text-dark border mb-3">Since 1950</span>
            <h1 className="display-3 fw-bold mb-4" style={{ letterSpacing: '-2px' }}>
              Authentic <br/> 
              <span style={{ color: '#d97706' }}>Bikaner Taste.</span>
            </h1>
            <p className="lead text-secondary mb-5" style={{ maxWidth: '400px' }}>
              From crispy Bhujia to syrupy Rasgullas. Experience the legendary taste of Bikaner delivered to your door.
            </p>
            <div className="d-flex gap-3">
              <Link to="/shop" className="btn btn-primary btn-lg px-5">Shop Sweets</Link>
              <Link to="/register" className="btn btn-outline-dark btn-lg px-4">Sign Up</Link>
            </div>
          </div>

          {/* Right Image (Hero) */}
          <div className="col-md-6 text-center">
            <div style={{ position: 'relative' }}>
              {/* Decorative Circle */}
              <div style={{
                position: 'absolute', top: '10%', right: '10%', width: '80%', height: '80%',
                backgroundColor: '#f3f4f6', borderRadius: '50%', zIndex: -1
              }}></div>
              <img 
                src="https://images.unsplash.com/photo-1599639668350-93a34a49df2d?q=80&w=800&auto=format&fit=crop" 
                alt="Delicious Sweets" 
                className="img-fluid"
                style={{ borderRadius: '24px', boxShadow: '0 25px 50px -12px rgba(0, 0, 0, 0.15)' }}
              />
            </div>
          </div>

        </div>
      </div>
    </div>
  );
};

export default HomePage;