import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

import { listOperators } from '../../services/api';

import './style.css';

export default function Header() {
  const [ operatorLogged, setOperatorLogged ] = useState([]);
  const [ operators, setOperators ] = useState([]);
  const [ open, setOpen ] = useState(false);


  useEffect(() => {
    async function fetchData() {
      setOperators(await listOperators());
    }
    fetchData();
  }, []);

  // const switchOperator = (operatorCode) => {
  //   localStorage.setItem('operatorCode', operatorCode);
  // }

  const modalAlterOperator = () => {
    console.log(open);
    setOpen(!open);
  }

  return (
    <header className="header">
      <Link id="main_home" to="/">
        <div className="link_button">Início</div>
      </Link>
      <Link id="main_operators" to="/operators">
        <div className="link_button">Operadores</div>
      </Link>
      <Link id="main_resident" to="/residents">
        <div className="link_button">Moradores</div>
      </Link>
      <Link id="main_deliveries" to="/deliveries">
        <div className="link_button">Entregas</div>
      </Link>

      <div onClick={() => modalAlterOperator()}>
        <div className="circle">
          {
            !operatorLogged ? operatorLogged : operators && operators[0] ? `${operators[0].firstName.charAt(0).toUpperCase()}${operators[0].lastName.charAt(0).toUpperCase()}` : "?"
          }
        </div>
      </div>
    </header>
  );
}
