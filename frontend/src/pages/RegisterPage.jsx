import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { registerUser } from '../services/authService';
import { toast } from 'react-toastify';

const RegisterPage = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: ''
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await registerUser(formData);
      toast.success("Registration Successful! Please Login.");
      navigate('/login');
    } catch (error) {
      toast.error(error.response?.data?.message || "Registration failed");
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card shadow border-0">
            <div className="card-header bg-dark text-white text-center py-3">
              <h4 className="mb-0">Create Account</h4>
            </div>
            <div className="card-body p-4">
              <form onSubmit={handleSubmit}>
                
                <div className="mb-3">
                  <label className="form-label">Username</label>
                  <input 
                    type="text" 
                    name="username"
                    className="form-control" 
                    value={formData.username}
                    onChange={handleChange}
                    required 
                  />
                </div>

                <div className="mb-3">
                  <label className="form-label">Email Address</label>
                  <input 
                    type="email" 
                    name="email"
                    className="form-control" 
                    value={formData.email}
                    onChange={handleChange}
                    required 
                  />
                </div>

                <div className="mb-3">
                  <label className="form-label">Password</label>
                  <input 
                    type="password" 
                    name="password"
                    className="form-control" 
                    value={formData.password}
                    onChange={handleChange}
                    required 
                  />
                </div>

                <button type="submit" className="btn btn-success w-100 btn-lg mt-3">Register</button>
              </form>
              
              <div className="text-center mt-3">
                <p>Already have an account? <Link to="/login" className="text-decoration-none">Login here</Link></p>
              </div>

            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RegisterPage;