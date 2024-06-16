import React from 'react';
import { Route, Routes, useLocation } from 'react-router-dom';
import Navbar from './components/Navbar';
import ItemsList from './components/ItemsList';
import UploadItem from './components/UploadItem';
import Packages from './components/Packages';
import AuthPage from './components/AuthPage';
import './App.css';

const App = () => {
  const location = useLocation();
  const isAuthPage = location.pathname === '/';
  
  return (
    <div className="app">
      {!isAuthPage && <Navbar />}
      <main className="content">
        <Routes>
          <Route path="/all-items" element={<ItemsList />} />
          <Route path="/upload-item" element={<UploadItem />} />
          <Route path="/packages" element={<Packages />} />
          <Route path="/" element={<AuthPage />} />
        </Routes>
      </main>
    </div>
  );
};

export default App;
