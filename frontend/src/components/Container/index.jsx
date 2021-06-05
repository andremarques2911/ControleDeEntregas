import React from 'react';

import './style.css';

export default function Container(props) {
  const { children } = props;
  return (
    <div className="container">
      { children }
    </div>
  );
}
