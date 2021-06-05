import React from 'react';
import Grow from '@material-ui/core/Grow';

import './style.css';

export default function Panel(props) {
  const { children } = props;
  return (
    <Grow in={true} {...({ timeout: 1000 })} disableStrictModeCompat={true}>
      <div className="panel">
        {  children }
      </div>
    </Grow>
  );
}
