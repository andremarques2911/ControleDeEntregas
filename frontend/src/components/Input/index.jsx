import React from 'react';

import './style.css';

export default function Input(props) {
  const { id, label, placeholder } = props;
  return (
    <React.Fragment>
      {
        label && (
          <div>
            <label htmlFor={id} className="label" >{label}: </label>
          </div>
        )
      }

      <div>
        <input type="text" id={id} className="input" placeholder={placeholder} />
      </div>
    </React.Fragment>
  );
}
