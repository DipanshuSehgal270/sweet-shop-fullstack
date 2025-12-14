import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getSweetById, updateSweet } from '../services/sweetService';
import { toast } from 'react-toastify';
import Navbar from '../components/Navbar';

const EditSweetPage = () => {
  const { id } = useParams(); // Get ID from URL (e.g., /edit-sweet/1)
  const navigate = useNavigate();

  // State
  const [name, setName] = useState('');
  const [price, setPrice] = useState('');
  const [quantity, setQuantity] = useState('');
  const [category, setCategory] = useState('');
  const [description, setDescription] = useState('');
  const [image, setImage] = useState(null); // New image (if selected)
  const [previewUrl, setPreviewUrl] = useState(''); // Current image preview

  // 1. Load Data on Mount
  useEffect(() => {
    loadSweetDetails();
  }, []);

  const loadSweetDetails = async () => {
    try {
      const sweet = await getSweetById(id);
      setName(sweet.name);
      setPrice(sweet.price);
      setQuantity(sweet.quantity);
      setCategory(sweet.category);
      setDescription(sweet.description);
      // Set preview to existing image from backend
      setPreviewUrl(`http://localhost:8080/api/sweets/${id}/image`);
    } catch (error) {
      toast.error("Failed to load sweet details");
      navigate('/');
    }
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setImage(file);
    // Create local preview for new image
    setPreviewUrl(URL.createObjectURL(file));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    const sweetData = JSON.stringify({ name, price, quantity, category, description });
    
    formData.append("sweet", sweetData);
    
    // Only append image if user selected a NEW one
    if (image) {
      formData.append("image", image);
    }

    try {
      await updateSweet(id, formData);
      toast.success("Sweet Updated Successfully!");
      navigate('/');
    } catch (error) {
      toast.error("Update failed");
    }
  };

  return (
    <div>
      <Navbar />
      <div className="container mt-4">
        <div className="card shadow-sm col-md-8 mx-auto">
          <div className="card-header bg-warning text-dark">
            <h4>Edit Sweet</h4>
          </div>
          <div className="card-body">
            <form onSubmit={handleSubmit}>
              
              <div className="mb-3 text-center">
                <label className="d-block mb-2">Current Image</label>
                <img src={previewUrl} alt="Preview" style={{ height: '150px', objectFit: 'cover', borderRadius: '8px' }} />
              </div>

              <div className="mb-3">
                <label>Name</label>
                <input type="text" className="form-control" value={name} onChange={e => setName(e.target.value)} required />
              </div>

              <div className="row mb-3">
                <div className="col">
                  <label>Price</label>
                  <input type="number" className="form-control" value={price} onChange={e => setPrice(e.target.value)} required />
                </div>
                <div className="col">
                  <label>Quantity</label>
                  <input type="number" className="form-control" value={quantity} onChange={e => setQuantity(e.target.value)} required />
                </div>
              </div>

              <div className="mb-3">
                <label>Category</label>
                <select className="form-select" value={category} onChange={e => setCategory(e.target.value)} required>
                  <option value="Milk">Milk Based</option>
                  <option value="Dry Fruit">Dry Fruit</option>
                  <option value="Syrup">Syrup Based</option>
                  <option value="Ghee">Ghee Based</option>
                </select>
              </div>

              <div className="mb-3">
                <label>Description</label>
                <textarea className="form-control" rows="2" value={description} onChange={e => setDescription(e.target.value)}></textarea>
              </div>

              <div className="mb-3">
                <label>Change Image (Optional)</label>
                <input type="file" className="form-control" onChange={handleImageChange} accept="image/*" />
              </div>

              <button type="submit" className="btn btn-warning w-100">Update Sweet</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EditSweetPage;