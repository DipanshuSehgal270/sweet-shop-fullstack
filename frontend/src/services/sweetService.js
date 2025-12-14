import api from './api';

// 1. Get all sweets
export const getAllSweets = async () => {
  const response = await api.get('/sweets');
  return response.data;
};

// 2. Search sweets (Name, Category, Price)
export const searchSweets = async (filters) => {
  const response = await api.get('/sweets/search', { params: filters });
  return response.data;
};

// 3. Purchase a sweet
export const purchaseSweet = async (id) => {
  const response = await api.post(`/sweets/${id}/purchase`);
  return response.data;
};

// 4. Get single sweet by ID (For Edit Page)
export const getSweetById = async (id) => {
  const response = await api.get(`/sweets/${id}`);
  return response.data;
};

// 5. Update sweet (For Edit Page)
export const updateSweet = async (id, formData) => {
  const response = await api.put(`/sweets/${id}`, formData);
  return response.data;
};

// 6. Delete sweet (For Admin Dashboard) <--- YOU WERE MISSING THIS
export const deleteSweet = async (id) => {
  const response = await api.delete(`/sweets/${id}`);
  return response.data;
};