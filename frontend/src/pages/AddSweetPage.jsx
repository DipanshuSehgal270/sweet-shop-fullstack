import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api'; // Use our api helper
import { toast } from 'react-toastify';
import Navbar from '../components/Navbar';

const AddSweetPage = () => {
  const navigate = useNavigate();
  
  // State for form fields
  const [name, setName] = useState('');
  const [price, setPrice] = useState('');
  const [quantity, setQuantity] = useState('');
  const [category, setCategory] = useState('');
  const [description, setDescription] = useState('');
  const [image, setImage] = useState(null);

  const handleImageChange = (e) => {
    setImage(e.target.files[0]); // Get the file object
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // 1. Create FormData (Required for Files)
    const formData = new FormData();
    
    // 2. Add JSON part (Must match backend @RequestPart("sweet"))
    const sweetData = JSON.stringify({
      name,
      price,
      quantity,
      category,
      description
    });
    formData.append("sweet", sweetData); // Key "sweet"

    // 3. Add File part (Must match backend @RequestPart("image"))
    if (image) {
      formData.append("image", image); // Key "image"
    } else {
        toast.error("Please select an image!");
        return;
    }

    try {
      // 4. Send Request (Content-Type is auto-set by Axios for FormData)
      await api.post('/sweets', formData);
      
      toast.success("Sweet Added Successfully!");
      navigate('/'); // Go back to Dashboard
    } catch (error) {
      console.error(error);
      toast.error("Failed to add sweet");
    }
  };

  return (
    <div>
      <Navbar />
      <div className="container mt-4">
        <div className="card shadow-sm col-md-8 mx-auto">
          <div className="card-header bg-success text-white">
            <h4>Add New Sweet</h4>
          </div>
          <div className="card-body">
            <form onSubmit={handleSubmit}>
              
              <div className="mb-3">
                <label className="form-label">Name</label>
                <input type="text" className="form-control" value={name} onChange={e => setName(e.target.value)} required />
              </div>

              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label">Price (₹)</label>
                  <input type="number" className="form-control" value={price} onChange={e => setPrice(e.target.value)} required />
                </div>
                <div className="col-md-6 mb-3">
                  <label className="form-label">Quantity</label>
                  <input type="number" className="form-control" value={quantity} onChange={e => setQuantity(e.target.value)} required />
                </div>
              </div>

              <div className="mb-3">
                <label className="form-label">Category</label>
                <select className="form-select" value={category} onChange={e => setCategory(e.target.value)} required>
                  <option value="">Select Category</option>
                  <option value="Milk">Milk Based</option>
                  <option value="Dry Fruit">Dry Fruit</option>
                  <option value="Syrup">Syrup Based</option>
                  <option value="Ghee">Ghee Based</option>
                </select>
              </div>

              <div className="mb-3">
                <label className="form-label">Description</label>
                <textarea className="form-control" rows="3" value={description} onChange={e => setDescription(e.target.value)}></textarea>
              </div>

              <div className="mb-3">
                <label className="form-label">Sweet Image</label>
                <input type="file" className="form-control" onChange={handleImageChange} accept="image/*" required />
              </div>

              <button type="submit" className="btn btn-success w-100">Add Sweet</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddSweetPage;