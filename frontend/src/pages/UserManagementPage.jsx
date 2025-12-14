import React, { useEffect, useState } from 'react';
import Navbar from '../components/Navbar';
import { getAllUsers, promoteToAdmin } from '../services/authService';
import { toast } from 'react-toastify';

const UserManagementPage = () => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    loadUsers();
  }, []);

  const loadUsers = async () => {
    try {
      const data = await getAllUsers();
      setUsers(data);
    } catch (error) {
      toast.error("Failed to load users");
    }
  };

  const handlePromote = async (id, username) => {
    if (!window.confirm(`Make ${username} an ADMIN? This cannot be undone.`)) return;
    try {
      await promoteToAdmin(id);
      toast.success(`${username} is now an Admin`);
      loadUsers();
    } catch (error) {
      toast.error("Failed to promote");
    }
  };

  return (
    <div className="app-container">
      <Navbar />
      <div className="container mt-4">
        <h2 className="mb-4 fw-bold text-dark">User Management</h2>
        <div className="card shadow-sm border-0">
          <div className="card-body p-0">
            <table className="table table-hover mb-0">
              <thead className="bg-light">
                <tr>
                  <th className="p-3">ID</th>
                  <th className="p-3">Username</th>
                  <th className="p-3">Email</th>
                  <th className="p-3">Role</th>
                  <th className="p-3">Actions</th>
                </tr>
              </thead>
              <tbody>
                {users.map(user => (
                  <tr key={user.id}>
                    <td className="p-3">#{user.id}</td>
                    <td className="p-3 fw-bold">{user.username}</td>
                    <td className="p-3">{user.email}</td>
                    <td className="p-3">
                      <span className={`badge ${user.role === 'ADMIN' ? 'bg-danger' : 'bg-info'}`}>
                        {user.role}
                      </span>
                    </td>
                    <td className="p-3">
                      {user.role !== 'ADMIN' && (
                        <button 
                          className="btn btn-sm btn-outline-danger"
                          onClick={() => handlePromote(user.id, user.username)}
                        >
                          Promote to Admin
                        </button>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserManagementPage;