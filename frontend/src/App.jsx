import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import { CartProvider } from './context/CartContext'; 
import 'react-toastify/dist/ReactToastify.css';

// Pages
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import DashboardPage from './pages/DashboardPage';
import AddSweetPage from './pages/AddSweetPage';
import EditSweetPage from './pages/EditSweetPage';
import CartPage from './pages/CartPage';
import UserManagementPage from './pages/UserManagementPage';

// Components
import Footer from './components/Footer'; // <--- Import Footer

function App() {
  return (
    <CartProvider>
      <Router>
        {/* Main Flex Container for Sticky Footer */}
        <div className="d-flex flex-column min-vh-100">
          
          <ToastContainer position="top-right" autoClose={3000} />
          
          {/* Main Content Area (Grows to fill space) */}
          <div className="flex-grow-1">
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/shop" element={<DashboardPage />} />
              <Route path="/login" element={<LoginPage />} />
              <Route path="/register" element={<RegisterPage />} />
              <Route path="/cart" element={<CartPage />} />
              
              {/* Admin Routes */}
              <Route path="/add-sweet" element={<AddSweetPage />} />
              <Route path="/edit-sweet/:id" element={<EditSweetPage />} />
              <Route path="/admin/users" element={<UserManagementPage />} />
            </Routes>
          </div>

          {/* Footer at the bottom */}
          <Footer />
          
        </div>
      </Router>
    </CartProvider>
  );
}

export default App;