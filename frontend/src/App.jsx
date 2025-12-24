import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
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
import Footer from './components/Footer';

function App() {
  return (
    <Router>
      <div className="d-flex flex-column min-vh-100">
        
        <ToastContainer position="top-right" autoClose={3000} />
        
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

        <Footer />
        
      </div>
    </Router>
  );
}

export default App;