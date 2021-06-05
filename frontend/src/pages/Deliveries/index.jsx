import React, { useState, useEffect } from 'react';

import Swal from 'sweetalert2';

import Header from '../../components/Header';
import Panel from '../../components/Panel';
import Container from '../../components/Container';
import Icon from '../../components/Icon';
import TableDeliveries from '../../components/Tables/TableDeliveries';
import { listDeliveries, saveDeliveries, registerWithdrawal } from '../../services/api.js';

import './style.css';

export default function Deliveries() {
  const [ deliveries, setDeliveries ] = useState([]);

  useEffect(() => {
    async function fetchData() {
      setDeliveries(await listDeliveries());
    }
    fetchData();
  }, []);

  const save = async () => {
    const { value: formValues } = await Swal.fire({
      title: 'Registrar retirada',
      html:
        '<input type="text" id="swal-input1" class="swal2-input" placeholder="Descrição da entrega...">' +
        '<input type="number" min="0" id="swal-input2" class="swal2-input" placeholder="Número do apartamento...">',
      focusConfirm: true,
      showCloseButton: true,
      showCancelButton: true,
      confirmButtonText: 'Registrar',
      cancelButtonText: "Cancelar",
      cancelButtonColor: "rgb(255, 0, 0)",
      preConfirm: () => {
        return {
          description: document.getElementById('swal-input1').value,
          apartment: document.getElementById('swal-input2').value
        }
      }
    });
    if (formValues) {
      if (formValues.description.length === 0) {
        Swal.fire('Ops!', 'Favor preencher o campo Descrição da entrega.','warning');
        return;
      } else if (formValues.apartment.length === 0) {
        Swal.fire('Ops!', 'Favor preencher o campo Número do apartamento.','warning');
        return;
      }
      const newDelivery = await saveDeliveries(formValues);
      if (newDelivery) {
        Swal.fire('Sucesso!', 'Entrega adicionada com sucesso.','success').then(function() {
          window.location.href = window.location.href;
        });
      }
    }
  }

  const editWthdrawal = async (evt) => {
    const { value: formValues } = await Swal.fire({
      title: 'Registrar retirada',
      html:
        '<input type="number" min="0" id="swal-input1" class="swal2-input" placeholder="Código do morador...">',
      focusConfirm: true,
      showCloseButton: true,
      showCancelButton: true,
      confirmButtonText: 'Registrar',
      cancelButtonText: "Cancelar",
      cancelButtonColor: "rgb(255, 0, 0)",
      preConfirm: () => {
        return [
          document.getElementById('swal-input1').value.length > 0,
          document.getElementById('swal-input1').value
        ]
      }
    });
    if (typeof formValues === 'undefined') {
      return;
    }
    if (!formValues[0]) {
      Swal.fire('Ops!', 'Favor preencher o campo Código do Morador.','warning');
      return;
    }
    const residentCode = formValues[1];
    const deliveryCode = evt.target.id;
    const newWithdrawal = await registerWithdrawal({residentCode, deliveryCode});
    if (newWithdrawal) {
      Swal.fire('Sucesso!', 'Retirada realizada com sucesso.','success').then(function() {
        window.location.href = window.location.href;
      });
    }
  }

  return (
    <React.Fragment>
      <Header />
      <Container>
        <Panel>
          <div className="panel_content">
            <div className="page-title">Entregas</div>
            <div className="register_deliveries">
              <Icon id={"save_deliveries"} icon={"far fa-plus-square"} title={"Cadastrar entrega"} color={"green"} onClick={() => save()} />
            </div>
            <TableDeliveries deliveries={deliveries} onClick={(evt) => editWthdrawal(evt)} />
          </div>
        </Panel>
      </Container>
    </React.Fragment>
  );
}
