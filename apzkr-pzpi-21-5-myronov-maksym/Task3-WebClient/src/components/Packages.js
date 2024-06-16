import React from 'react';
import './Packages.css'; // Create this CSS file for the styles

const Packages = () => {
  return (
    <div className="packages">
      <h1>Packages</h1>
      <div className="package-item">
        <span>Package 1</span>
        <span>Unpacked</span>
      </div>
      <div className="package-item">
        <span>Package 2</span>
        <span>In transit</span>
      </div>
    </div>
  );
};

export default Packages;
