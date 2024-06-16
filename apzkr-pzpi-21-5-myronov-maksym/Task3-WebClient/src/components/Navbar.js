import React from 'react';
import { NavLink } from 'react-router-dom';
import './Navbar.css';

const Navbar = () => {
  return (
    <nav className="navbar">
      <div className="logo">
        <img src={`${process.env.PUBLIC_URL}/logo.jpg`} alt="Second Hand Sync" />
      </div>
      <ul>
        <li>
          <NavLink to="/all-items" activeClassName="active-link">All items</NavLink>
        </li>
        <li>
          <NavLink to="/packages" activeClassName="active-link">Packages</NavLink>
        </li>
        <li>
          <NavLink to="/upload-item" activeClassName="active-link">Upload item</NavLink>
        </li>
      </ul>
    </nav>
  );
};

export default Navbar;
