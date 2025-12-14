import React from 'react';

const Footer = () => {
  return (
    <footer className="mt-auto py-5" style={{ backgroundColor: '#fff', borderTop: '1px solid #eaeaea' }}>
      <div className="container">
        <div className="row">
          
          {/* Column 1: Brand & Message */}
          <div className="col-md-4 mb-4 mb-md-0">
            <h5 className="fw-bold mb-3" style={{ color: '#d97706' }}>🥨 Bikaner Sweets</h5>
            <p className="text-secondary small">
              Serving authentic flavors since 1950. We promise quality, purity, and a taste that brings you back home.
            </p>
            <p className="small text-muted">
              &copy; {new Date().getFullYear()} Bikaner Sweets. All rights reserved.
            </p>
          </div>

          {/* Column 2: Quick Links */}
          <div className="col-md-4 mb-4 mb-md-0">
            <h6 className="fw-bold mb-3">Shop</h6>
            <ul className="list-unstyled small text-secondary">
              <li className="mb-2"><a href="/shop" className="text-decoration-none text-secondary">All Sweets</a></li>
              <li className="mb-2"><a href="#" className="text-decoration-none text-secondary">Best Sellers</a></li>
              <li className="mb-2"><a href="#" className="text-decoration-none text-secondary">Gift Boxes</a></li>
            </ul>
          </div>

          {/* Column 3: Contact & Warm Closing */}
          <div className="col-md-4 text-md-end">
            <h6 className="fw-bold mb-3">Visit Us</h6>
            <p className="small text-secondary mb-1">Sector 22-B, Chandigarh</p>
            <p className="small text-secondary mb-3">Phone: +91 98765 43210</p>
            
            <div className="mt-3">
              <span className="badge bg-warning text-dark border p-2">
                Come back soon! 🧡
              </span>
            </div>
          </div>

        </div>
      </div>
    </footer>
  );
};

export default Footer;