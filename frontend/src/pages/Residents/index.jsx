import React, { useState, useEffect } from 'react';

import TextField from '@material-ui/core/TextField';

import Swal from 'sweetalert2';

import Header from '../../components/Header';
import Panel from '../../components/Panel';
import Container from '../../components/Container';
import Icon from '../../components/Icon';
import TableResidents from '../../components/Tables/TableResidents';
import { listResidents, saveResident, deactivateResident  } from '../../services/api';

import './style.css';

export default function Residents() {
  const [ residents, setResidents ] = useState([]);
  const [ name, setName ] = useState("");
  const [ rg, setRg ] = useState("");
  const [ apartment, setApartment ] = useState("");

  useEffect(() => {
    async function fetchData() {
      setResidents(await listResidents());
    }
    fetchData();
  }, []);

  const save = async () => {
    if (!name && name.length === 0) {
      Swal.fire('Ops!', 'Favor preencher o campo Nome.','warning');
      return;
    }
    if (!rg && rg.length === 0) {
      Swal.fire('Ops!', 'Favor preencher o campo Rg.','warning');
      return;
    }
    if (!apartment && apartment.length === 0) {
      Swal.fire('Ops!', 'Favor preencher o campo Apartamento.','warning');
      return;
    }
    const newResident = await saveResident({name, rg, apartment});
    if (newResident) {
      Swal.fire('Sucesso!', 'Morador adicionado com sucesso.','success').then(function() {
        window.location.href = window.location.href;
      });
    }
  }

  const deactivate = async (evt) => {
    const id = evt.target.id;
    const deletedResident = await deactivateResident(id);
    if (deletedResident) {
      Swal.fire('Sucesso!', 'Morador deletado com sucesso.','success').then(function() {
        window.location.href = window.location.href;
      });
    }
  }

  const changeInputs = (evt) => {
    const key = evt.target.id;
    const value = evt.target.value;
    switch (key) {
        case "name":
            setName(value);
            break;
        case "rg":
          setRg(value);
          break;
        case "apartment":
          setApartment(value);
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
            <div className="page-title">Moradores</div>
            <div className="register">
              <TextField
                id="name"
                label="Nome"
                variant="outlined"
                onChange={changeInputs.bind(this)}
              />
              <TextField
                id="rg"
                label="Rg"
                variant="outlined"
                onChange={changeInputs.bind(this)}
              />
              <TextField
                id="apartment"
                label="Apartamento"
                variant="outlined"
                onChange={changeInputs.bind(this)}
              />
              <Icon id={"save_resident"} icon={"far fa-plus-square"} title={"Cadastrar morador"} color={"green"} onClick={() => save()} />
            </div>
            <TableResidents residents={residents} onClick={(evt) => deactivate(evt)} />
          </div>
        </Panel>
      </Container>
    </React.Fragment>
  );
}
