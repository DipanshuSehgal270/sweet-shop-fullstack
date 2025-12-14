import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';
// Import services
import { getAllSweets, purchaseSweet, searchSweets, deleteSweet } from '../services/sweetService';
import { isAdmin } from '../services/authService';
// Import Cart Context
import { useCart } from '../context/CartContext';
import { toast } from 'react-toastify';
import { Link } from 'react-router-dom';

const DashboardPage = () => {
  const [sweets, setSweets] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const { addToCart } = useCart(); // Access Global Cart

  // 1. Load Data on Mount
  useEffect(() => {
    loadSweets();
  }, []);

  const loadSweets = async () => {
    try {
      const data = await getAllSweets();
      if (Array.isArray(data)) {
        setSweets(data);
      }
    } catch (error) {
      toast.error("Failed to load sweets");
    }
  };

  // 2. Search Logic
  const handleSearch = async (e) => {
    e.preventDefault();
    try {
      const data = await searchSweets({ name: searchTerm });
      setSweets(data);
    } catch (error) {
      toast.error("Search failed");
    }
  };

  // 3. Delete Logic (Admin Only)
  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this sweet?")) return;
    try {
      await deleteSweet(id);
      toast.success("Sweet Deleted");
      loadSweets(); // Refresh list
    } catch (error) {
      toast.error("Delete failed");
    }
  };

  return (
    <div>
      <Navbar />
      <div className="container pb-5">
        
        {/* --- 🌟 HERO BANNER SECTION (UPDATED IMAGE) --- */}
          <div className="mb-5 rounded-4 position-relative overflow-hidden shadow-sm" style={{ height: '320px' }}>
              {/* Background Image: Authentic Indian Sweets Mix */}
            <img 
              src="/banner.jpg"  
              alt="Bikaner Sweets Banner" 
                style={{ width: '100%', height: '100%', objectFit: 'cover', filter: 'brightness(0.4)' }}
            />
            
            {/* Content Overlay */}
            <div className="position-absolute top-50 start-0 translate-middle-y p-5 text-white">
                <span className="badge bg-warning text-dark mb-2">Since 1950</span>
                <h1 className="display-4 fw-bold mb-2">Pure Taste, <br/> Pure Tradition.</h1>
                <p className="lead mb-4 opacity-75" style={{ maxWidth: '500px', fontSize: '1.1rem' }}>
                    Handcrafted daily using 100% pure ghee and organic ingredients. The authentic taste of Bikaner.
                </p>
                
                {/* Trust Badges */}
                <div className="d-flex gap-4 pt-2">
                    <div className="d-flex align-items-center gap-2">
                        <span style={{ fontSize: '1.2rem' }}>🥇</span>
                        <span className="fw-bold small text-uppercase">100% Pure</span>
                    </div>
                    <div className="d-flex align-items-center gap-2">
                        <span style={{ fontSize: '1.2rem' }}>🚚</span>
                        <span className="fw-bold small text-uppercase">Fast Delivery</span>
                    </div>
                    <div className="d-flex align-items-center gap-2">
                        <span style={{ fontSize: '1.2rem' }}>⭐</span>
                        <span className="fw-bold small text-uppercase">4.9/5 Rated</span>
                    </div>
                </div>
            </div>
        </div>

        {/* --- 🔥 TRENDING & OFFERS SECTION --- */}
        <div className="mb-5">
            <h4 className="fw-bold mb-3 d-flex align-items-center gap-2" style={{color: '#111'}}>
                <span>🔥</span> Trending Now
            </h4>
            <div className="row g-4">
                
                {/* 1. TOP 2 BEST SELLERS (Logic to check 'soldCount') */}
                {sweets
                    .sort((a, b) => (b.soldCount || 0) - (a.soldCount || 0)) // Sort by sales count
                    .slice(0, 2) // Pick Top 2
                    .map(sweet => (
                        <div className="col-md-6" key={'trend-' + sweet.id}>
                            <div className="card border-0 bg-white shadow-sm h-100 flex-row overflow-hidden align-items-center p-2">
                                <div style={{width: '120px', height: '120px', flexShrink: 0}}>
                                    <img 
                                        src={`http://localhost:8080/api/sweets/${sweet.id}/image`} 
                                        style={{width: '100%', height: '100%', objectFit: 'cover', borderRadius: '8px'}}
                                        onError={(e) => e.target.src = 'https://via.placeholder.com/150'}
                                        alt={sweet.name}
                                    />
                                </div>
                                <div className="card-body py-0 ps-3">
                                    <div className="badge bg-warning text-dark mb-1" style={{fontSize: '0.7rem'}}>
                                        #{sweet.soldCount > 0 ? 'Best Seller' : 'Featured'}
                                    </div>
                                    <h5 className="fw-bold mb-1">{sweet.name}</h5>
                                    <p className="small text-secondary mb-2">
                                        {sweet.soldCount > 0 
                                            ? `${sweet.soldCount} happy customers!` 
                                            : 'Fresh from kitchen!'}
                                    </p>
                                    <button 
                                        className="btn btn-sm btn-dark px-3"
                                        onClick={() => {addToCart(sweet); toast.success("Added Best Seller! 🌟")}}
                                        disabled={sweet.quantity <= 0}
                                    >
                                        Add - ₹{sweet.price}
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))}
                
                {/* 2. Special Combo Deal */}
                <div className="col-12 mt-4">
                    <div className="card border-0 text-white shadow" style={{ background: 'linear-gradient(135deg, #111 0%, #333 100%)', borderRadius: '16px' }}>
                        <div className="card-body p-4 d-flex justify-content-between align-items-center flex-wrap gap-3">
                            <div>
                                <span className="badge bg-danger mb-2">Limited Time Offer</span>
                                <h3 className="mb-1">🎁 Festival Combo Pack</h3>
                                <p className="mb-0 opacity-75">1kg Gulab Jamun + 500g Kaju Katli + Premium Gift Box</p>
                            </div>
                            <div className="text-end">
                                <div className="mb-2">
                                    <span className="text-decoration-line-through opacity-50 me-2">₹1200</span>
                                    <span className="h3 text-warning fw-bold">₹899</span>
                                </div>
                                <button className="btn btn-light fw-bold px-4" onClick={() => toast.info("Combo added to wishlist!")}>
                                    Grab Deal
                                </button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>

        {/* --- 🔍 SEARCH & MAIN GRID CONTROLS --- */}
        <div className="d-flex justify-content-between align-items-center mb-4 mt-5 border-bottom pb-3">
            <h4 className="fw-bold m-0 text-dark">Our Collection</h4>
            
            <div className="d-flex gap-2">
                <form onSubmit={handleSearch} className="d-flex gap-2">
                    <input 
                        type="text" 
                        className="form-control" 
                        placeholder="Search sweets..." 
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        style={{ width: '250px' }}
                    />
                    <button type="submit" className="btn btn-dark">Search</button>
                    {searchTerm && (
                        <button type="button" className="btn btn-outline-secondary" onClick={() => {setSearchTerm(''); loadSweets()}}>✕</button>
                    )}
                </form>

                {isAdmin() && (
                    <Link to="/add-sweet" className="btn btn-success ms-2 text-nowrap">+ Add Item</Link>
                )}
            </div>
        </div>

        {/* --- 🍬 MAIN SWEETS GRID --- */}
        <div className="row">
          {sweets.map(sweet => (
            <div className="col-md-4 mb-4" key={sweet.id}>
              <div className="card h-100 border-0">
                
                {/* Image Wrapper (CSS Class handles Square Shape) */}
                <div className="card-image-wrapper">
                    <img 
                      src={`http://localhost:8080/api/sweets/${sweet.id}/image`} 
                      alt={sweet.name}
                      onError={(e) => {
                        e.target.onerror = null;
                        e.target.src = 'https://via.placeholder.com/400?text=Sweet';
                      }} 
                    />
                    {/* Stock Overlay */}
                    <div className="position-absolute top-0 end-0 m-3">
                        <span className={`badge ${sweet.quantity > 0 ? 'bg-success' : 'bg-danger'} shadow-sm`}>
                            {sweet.quantity > 0 ? 'In Stock' : 'Sold Out'}
                        </span>
                    </div>
                </div>
                
                <div className="card-body">
                  <div className="d-flex justify-content-between align-items-start mb-2">
                    <h5 className="card-title mb-0 text-truncate">{sweet.name}</h5>
                    <span className="text-primary fw-bold">₹{sweet.price}</span>
                  </div>
                  
                  <p className="card-text small text-muted text-truncate">
                    {sweet.description || "A delicious traditional sweet made with pure ingredients."}
                  </p>
                  
                  <div className="mt-3 d-flex align-items-center gap-2">
                      {/* ADD TO CART BUTTON */}
                      <button 
                        className="btn btn-dark flex-grow-1" 
                        onClick={() => {
                            addToCart(sweet);
                            toast.success("Added to Cart 🛒");
                        }}
                        disabled={sweet.quantity <= 0}
                      >
                        {sweet.quantity > 0 ? 'Add to Cart' : 'Out of Stock'}
                      </button>

                      {/* ADMIN ACTIONS */}
                      {isAdmin() && (
                        <>
                            <Link to={`/edit-sweet/${sweet.id}`} className="btn btn-outline-warning btn-sm" title="Edit">✏️</Link>
                            <button className="btn btn-outline-danger btn-sm" title="Delete" onClick={() => handleDelete(sweet.id)}>🗑️</button>
                        </>
                      )}
                  </div>
                </div>

              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;