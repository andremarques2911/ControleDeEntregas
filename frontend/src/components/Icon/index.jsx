import React from 'react';

import './style.css';

export default function Icon(props) {
  const { id, icon, title, color, classes, onClick } = props;
  return (
    <div className={`${classes}`}>
      <i id={id} className={`${icon + " " + color} icon`} title={ title } onClick={onClick}></i>
    </div>
  );
}
