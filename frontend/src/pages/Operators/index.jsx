import React, { useState, useEffect } from 'react';

import Swal from 'sweetalert2';

import TextField from '@material-ui/core/TextField';

import Header from '../../components/Header';
import Panel from '../../components/Panel';
import Container from '../../components/Container';
import Icon from '../../components/Icon';
import TableOperators from '../../components/Tables/TableOperators';
import { listOperators, saveOperator, deleteOperator  } from '../../services/api';

import './style.css';

export default function Operators() {
  const [ operators, setOperators ] = useState([]);
  const [ firstName, setFirstName ] = useState("");
  const [ lastName, setLastName ] = useState("");

  useEffect(() => {
    async function fetchData() {
      setOperators(await listOperators());
    }
    fetchData();
  }, []);

  const save = async () => {
    if (!firstName && firstName.length === 0) {
      Swal.fire('Ops!', 'Favor preencher o campo Primeiro nome.','warning');
      return;
    }
    if (!lastName && lastName.length === 0) {
      Swal.fire('Ops!', 'Favor preencher o campo Úlimo nome.','warning');
      return;
    }
    const newOperator = await saveOperator({firstName, lastName});
    if (newOperator) {
      Swal.fire('Sucesso!', 'Operador adicionado com sucesso.','success').then(function() {
        window.location.href = window.location.href;
      });
    }
  }

  const exclude = async (evt) => {
    const id = evt.target.id;
    const deletedOperator = await deleteOperator(id);
    if (deletedOperator) {
      Swal.fire('Sucesso!', 'Operador deletedo com sucesso.','success').then(function() {
        window.location.href = window.location.href;
      });
    }
  }

  const changeInputs = (evt) => {
    const key = evt.target.id;
    const value = evt.target.value;
    switch (key) {
        case "first_name":
            setFirstName(value);
            break;
        case "last_name":
            setLastName(value);
            break;
        default:
            break;
    }
  }

  return (
    <React.Fragment>
      <Header />
      <Container>
        <Panel>
          <div className="panel_content">
            <div className="page_title">Operadores</div>

              <div className="register">
                <TextField
                  id="first_name"
                  label="Primeiro nome"
                  variant="outlined"
                  onChange={changeInputs.bind(this)}
                />
                <TextField
                  id="last_name"
                  label="Segundo nome"
                  variant="outlined"
                  onChange={changeInputs.bind(this)}
                />
                <Icon id={"save_operator"} icon={"far fa-plus-square"} title={"Cadastrar operador"} color={"green"} onClick={() => save()} />
              </div>
              <TableOperators operators={operators} onClick={(evt) => exclude(evt)} />
          </div>
        </Panel>
      </Container>
    </React.Fragment>
  );
}
