import React, { useState } from 'react';
import Navbar from '../components/Navbar';
import { useCart } from '../context/CartContext';
import { purchaseSweet } from '../services/sweetService';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import PaymentModal from '../components/PaymentModal';

const CartPage = () => {
  const { cart, removeFromCart, clearCart } = useCart();
  const navigate = useNavigate();
  const [showPayment, setShowPayment] = useState(false); // Controls the modal

  const total = cart.reduce((sum, item) => sum + item.price, 0);

  const handlePaymentSuccess = async () => {
    try {
      // Process each item in the cart
      // In a real app, you would send the whole cart array to one backend endpoint
      for (const item of cart) {
        await purchaseSweet(item.id);
      }
      
      clearCart();
      setShowPayment(false);
      toast.success("Payment Successful! Order Placed.");
      navigate('/shop');
    } catch (error) {
      console.error(error);
      toast.error("Transaction failed. Some items might be out of stock.");
      setShowPayment(false);
    }
  };

  return (
    <div>
      <Navbar />
      <div className="container mt-5">
        <h2 className="mb-4 fw-bold">Your Cart ({cart.length})</h2>
        
        <div className="row">
          {/* LEFT COLUMN: Cart Items */}
          <div className="col-md-8">
            {cart.length === 0 ? (
                <div className="p-5 text-center bg-white border rounded shadow-sm">
                    <span style={{ fontSize: '3rem' }}>🛒</span>
                    <p className="text-muted mt-3">Your cart is empty.</p>
                    <a href="/shop" className="btn btn-primary mt-2">Start Shopping</a>
                </div>
            ) : (
                <ul className="list-group shadow-sm border-0">
                {cart.map((item, index) => (
                    <li key={index} className="list-group-item d-flex justify-content-between align-items-center p-3 border-bottom">
                    <div className="d-flex align-items-center gap-3">
                        <img 
                            src={`http://localhost:8080/api/sweets/${item.id}/image`} 
                            alt={item.name}
                            style={{width: '60px', height: '60px', objectFit: 'cover', borderRadius: '8px'}}
                            onError={(e) => e.target.src = 'https://via.placeholder.com/60?text=Sweet'}
                        />
                        <div>
                            <h6 className="mb-0 fw-bold">{item.name}</h6>
                            <small className="text-muted">{item.category}</small>
                        </div>
                    </div>
                    <div className="d-flex align-items-center gap-4">
                        <span className="fw-bold">₹{item.price}</span>
                        <button 
                            className="btn btn-sm btn-outline-danger border-0" 
                            onClick={() => removeFromCart(index)}
                            title="Remove Item"
                        >
                            ✕
                        </button>
                    </div>
                    </li>
                ))}
                </ul>
            )}
          </div>

          {/* RIGHT COLUMN: Summary & Checkout */}
          <div className="col-md-4 mt-4 mt-md-0">
            <div className="card border-0 bg-white shadow-sm p-4 sticky-top" style={{ top: '100px' }}>
                <h5 className="mb-3 fw-bold">Order Summary</h5>
                <div className="d-flex justify-content-between mb-2 text-secondary">
                    <span>Subtotal</span>
                    <span>₹{total}</span>
                </div>
                <div className="d-flex justify-content-between mb-4 text-secondary">
                    <span>Tax (GST Included)</span>
                    <span>₹0</span>
                </div>
                <hr style={{ borderColor: '#eee' }} />
                <div className="d-flex justify-content-between mb-4">
                    <strong className="h5">Total</strong>
                    <strong className="h5 text-primary">₹{total}</strong>
                </div>
                
                <button 
                    onClick={() => setShowPayment(true)} 
                    className="btn btn-dark w-100 py-3 fw-bold" 
                    disabled={cart.length === 0}
                >
                    Proceed to Pay
                </button>
                
                <div className="mt-3 text-center">
                    <small className="text-muted" style={{ fontSize: '0.75rem' }}>
                        🔒 Secure Checkout powered by RazorFake
                    </small>
                </div>
            </div>
          </div>
        </div>

        {/* PAYMENT MODAL (Only shows when showPayment is true) */}
        {showPayment && (
            <PaymentModal 
                total={total} 
                onClose={() => setShowPayment(false)} 
                onConfirm={handlePaymentSuccess} 
            />
        )}

      </div>
    </div>
  );
};

export default CartPage;