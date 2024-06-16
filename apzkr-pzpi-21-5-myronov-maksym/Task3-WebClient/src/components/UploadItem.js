import React from 'react';
import './UploadItem.css'; // Assuming you have some styles for the upload item page

const UploadItem = () => {
  return (
    <div className="upload-item">
      <h1>Upload Item</h1>
      <form>
        <label>
          Item Name:
          <input type="text" name="name" />
        </label>
        <label>
          Item Image:
          <input type="file" name="image" />
        </label>
        <button type="submit">Upload</button>
      </form>
    </div>
  );
};

export default UploadItem;
