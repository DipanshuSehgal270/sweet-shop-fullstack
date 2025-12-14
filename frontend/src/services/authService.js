import api from './api';
import { jwtDecode } from 'jwt-decode'; 

export const registerUser = async (userData) => {
  const response = await api.post('/auth/register', userData);
  return response.data;
};

export const loginUser = async (credentials) => {
  const response = await api.post('/auth/login', credentials);
  const token = response.data; 
  
  if (token) {
    localStorage.setItem('token', token);
    return token;
  }
  return null;
};

export const logoutUser = () => {
  localStorage.removeItem('token');
};

export const isAdmin = () => {
  const token = localStorage.getItem('token');
  if (!token) return false;
  
  try {
    const decoded = jwtDecode(token);
    return decoded.roles && decoded.roles[0] === 'ROLE_ADMIN';
  } catch (error) {
    return false;
  }
};

export const getUsername = () => {
  const token = localStorage.getItem('token');
  if (!token) return null;
  try {
    const decoded = jwtDecode(token);
    return decoded.sub; 
  } catch (e) {
    return null;
  }
};

export const getAllUsers = async () => {
  const response = await api.get('/auth/users');
  return response.data;
};

// Promote to Admin
export const promoteToAdmin = async (userId) => {
  const response = await api.put(`/auth/users/${userId}/promote`);
  return response.data;
};