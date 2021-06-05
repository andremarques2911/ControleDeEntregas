import * as axios from 'axios';

import Swal from 'sweetalert2';

const baseUrl = 'http://localhost:8080';
const urlOperators = baseUrl + '/operators';
const urlResidents = baseUrl + '/residents';
const urlDeliveries = baseUrl + '/deliveries';

export const saveDeliveries = async (deliveries) => {
  const operatorCode = localStorage.getItem('operatorCode');
  deliveries.operatorCode = operatorCode;
  console.log(deliveries)
  const response = await axios.post(`${urlDeliveries}/`, deliveries)
    .catch(error => {
      Swal.fire('Erro!', error.response.data.message,'error');
    });
    if (response) {
      return response.data;
    }
}

export const listDeliveries = async () => {
  const response = await axios.get(`${urlDeliveries}/generateReport`)
    .catch(error => {
      Swal.fire('Erro!', error.response.data.message,'error');
    });
    if (response) {
      return response.data;
    }
}

export const searchDashboardInformation = async () => {
  const response = await axios.get(`${urlDeliveries}/searchDashboardInformation`)
    .catch(error => {
      Swal.fire('Erro!', error.response.data.message,'error');
    });
    if (response) {
      return response.data;
    }
}

export const registerWithdrawal = async (delivery) => {
  const response = await axios.put(`${urlDeliveries}/`, delivery)
  .catch(error => {
    Swal.fire('Erro!', error.response.data.message,'error');
  });
  if (response) {
    return response.data;
  }
}

export const saveResident = async (resident) => {
  const response = await axios.post(`${urlResidents}/`, resident)
    .catch(error => {
      Swal.fire('Erro!', error.response.data.message,'error');
    });
    if (response) {
      return response.data;
    }
}

export const listResidents = async () => {
  const response = await axios.get(`${urlResidents}/`)
    .catch(error => {
      Swal.fire('Erro!', error.response.data.message,'error');
    });
    if (response) {
      return response.data;
    }
}

export const deactivateResident = async (id) => {
  const response = await axios.put(`${urlResidents}/${id}`)
    .catch(error => {
      Swal.fire('Erro!', error.response.data.message,'error');
    });
    if (response) {
      return response.data;
    }
}


export const saveOperator = async (operator) => {
  const response = await axios.post(`${urlOperators}/`, operator)
    .catch(error => {
      Swal.fire('Erro!', error.response.data.message,'error');
    });
    if (response) {
      return response.data;
    }
}

export const listOperators = async () => {
  const response = await axios.get(`${urlOperators}/`)
    .catch(error => {
      Swal.fire('Erro!', error.response.data.message,'error');
    });
    if (response) {
      return response.data;
    }
}

export const deleteOperator = async (id) => {
  const response = await axios.delete(`${urlOperators}/${id}`)
    .catch(error => {
      Swal.fire('Erro!', error.response.data.message,'error');
    });
    if (response) {
      return response.data;
    }
}
