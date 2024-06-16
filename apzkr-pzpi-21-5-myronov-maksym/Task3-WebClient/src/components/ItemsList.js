import React from 'react';
import './ItemsList.css'; // Assuming you have some styles for the items list

const ItemsList = () => {
  return (
    <div className="items-list">
      <h1>Items List</h1>
      <div className="item">
        <img src="path/to/image1" alt="Item 1" />
        <p>image1.name</p>
      </div>
      <div className="item">
        <img src="path/to/image2" alt="Item 2" />
        <p>image2.name</p>
      </div>
      <div className="item">
        <img src="path/to/image3" alt="Item 3" />
        <p>image3.name</p>
      </div>
      <div className="item">
        <img src="path/to/image4" alt="Item 4" />
        <p>image4.name</p>
      </div>
    </div>
  );
};

export default ItemsList;
