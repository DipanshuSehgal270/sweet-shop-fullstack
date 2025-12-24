import api from './api';

// 1. Get all sweets
export const getAllSweets = async () => {
  const response = await api.get('/sweets');
  return response.data;
};

// 2. Get Trending Sweets (New Optimized Endpoint)
export const getTrendingSweets = async () => {
  const response = await api.get('/sweets/trending');
  return response.data;
};

// 3. Search sweets (Name, Category, Price)
export const searchSweets = async (filters) => {
  const response = await api.get('/sweets/search', { params: filters });
  return response.data;
};

// 4. Purchase Batch (New Optimized Endpoint - One click, One call)
export const purchaseBatch = async (ids) => {
  const response = await api.post('/sweets/purchase/batch', ids);
  return response.data;
};

// 5. Get single sweet by ID (For Edit Page)
export const getSweetById = async (id) => {
  const response = await api.get(`/sweets/${id}`);
  return response.data;
};

// 6. Update sweet (For Edit Page)
export const updateSweet = async (id, formData) => {
  const response = await api.put(`/sweets/${id}`, formData);
  return response.data;
};

// 7. Delete sweet (For Admin Dashboard)
export const deleteSweet = async (id) => {
  const response = await api.delete(`/sweets/${id}`);
  return response.data;
};