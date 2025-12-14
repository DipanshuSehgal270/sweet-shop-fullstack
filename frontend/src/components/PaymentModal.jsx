import React, { useState } from 'react';

const PaymentModal = ({ total, onClose, onConfirm }) => {
  const [isProcessing, setIsProcessing] = useState(false);

  const handlePay = (e) => {
    e.preventDefault();
    setIsProcessing(true);
    
    // Simulate a 2-second bank delay
    setTimeout(() => {
      setIsProcessing(false);
      onConfirm(); // Trigger the actual purchase logic
    }, 2000);
  };

  return (
    <div className="modal d-block" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}>
      <div className="modal-dialog modal-dialog-centered">
        <div className="modal-content border-0 shadow-lg">
          
          <div className="modal-header bg-dark text-white">
            <h5 className="modal-title">Secure Checkout</h5>
            <button type="button" className="btn-close btn-close-white" onClick={onClose} disabled={isProcessing}></button>
          </div>

          <div className="modal-body p-4">
            <div className="alert alert-info d-flex align-items-center gap-2">
              <span>💳</span>
              <small>This is a simulated payment gateway.</small>
            </div>

            <h4 className="text-center mb-4">Total Amount: ₹{total}</h4>

            <form onSubmit={handlePay}>
              <div className="mb-3">
                <label className="form-label small text-secondary">CARD NUMBER</label>
                <input type="text" className="form-control" placeholder="0000 0000 0000 0000" required maxLength="19" />
              </div>

              <div className="row mb-4">
                <div className="col-6">
                  <label className="form-label small text-secondary">EXPIRY</label>
                  <input type="text" className="form-control" placeholder="MM/YY" required maxLength="5" />
                </div>
                <div className="col-6">
                  <label className="form-label small text-secondary">CVV</label>
                  <input type="password" className="form-control" placeholder="123" required maxLength="3" />
                </div>
              </div>

              <button type="submit" className="btn btn-success w-100 py-2" disabled={isProcessing}>
                {isProcessing ? (
                  <span><span className="spinner-border spinner-border-sm me-2"></span>Processing...</span>
                ) : (
                  `Pay ₹${total}`
                )}
              </button>
            </form>
          </div>

        </div>
      </div>
    </div>
  );
};

export default PaymentModal;