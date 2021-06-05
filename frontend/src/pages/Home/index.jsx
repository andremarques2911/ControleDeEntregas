import React, { useState, useEffect } from 'react';

import Header from '../../components/Header';

import { searchDashboardInformation  } from '../../services/api';

import './style.css';

export default function Home() {
  const [ dashboard, setDashboard ] = useState({});

  useEffect(() => {
    async function fetchData() {
      setDashboard(await searchDashboardInformation());
    }
    fetchData();
  }, []);
  return (
    <React.Fragment>
      <Header />
      <div className="panel_dashboard">
        <div className="card_dashboard">
          <div className="title_info">
            <h4 className="title">Número de entregas nos últimos 30 dias:</h4>
          </div>
          {
            dashboard && (
                <div className="field_info">{dashboard.numberDeliveriesLastThirtyDays}</div>
            )
          }
        </div>
        <div className="card_dashboard">
          <div lassName="title_info">
            <h4 className="title">Número de entregas não retiradas:</h4>
          </div>
          {
            dashboard && (
                <div className="field_info">{dashboard.numberDeliveriesNotWithdrawn}</div>
            )
          }
        </div>
        <div className="card_dashboard">
          <h4 className="title">Média entre registro de entrega e retirada:</h4>
          {
            dashboard && dashboard.averageDate && (
              <div className="average_informations">
                <div>{`${dashboard.averageDate.yearn ? dashboard.averageDate.yearn : 0} anos` }</div>
                <div>{`${dashboard.averageDate.month ? dashboard.averageDate.month : 0} meses` }</div>
                <div>{`${dashboard.averageDate.day ? dashboard.averageDate.day : 0} meses` }</div>
                <div>{`${dashboard.averageDate.hour ? dashboard.averageDate.hour : 0} horas` }</div>
                <div>{`${dashboard.averageDate.minute ? dashboard.averageDate.minute : 0} minutos` }</div>
                <div>{`${dashboard.averageDate.second ? dashboard.averageDate.second : 0} segundos` }</div>
              </div>
            )
          }
        </div>
      </div>
    </React.Fragment>
  );
}
