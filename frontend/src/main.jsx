import React from 'react'
import ReactDOM from 'react-dom/client' // <--- This was likely missing
import App from './App.jsx'
import './index.css' // Your professional styling
import 'bootstrap/dist/css/bootstrap.min.css'; // Bootstrap styles
import { CartProvider } from './context/CartContext'; // The Cart Logic

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <CartProvider>
      <App />
    </CartProvider>
  </React.StrictMode>,
)