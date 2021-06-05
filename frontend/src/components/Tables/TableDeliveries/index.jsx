import React from 'react';
import { withStyles, makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import Moment from 'moment';
import Icon from '../../Icon';

import '../style.css';

const StyledTableCell = withStyles((theme) => ({
  head: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  body: {
    fontSize: 14,
  },
}))(TableCell);

const useStyles = makeStyles({
  table: {
    minWidth: 700,
  },
});

export default function TableDeliveries(props) {
  const classes = useStyles();
  const { deliveries, onClick } = props;

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell>Entrega</StyledTableCell>
            <StyledTableCell align="right">Data/hora</StyledTableCell>
            <StyledTableCell align="right">Descrição</StyledTableCell>
            <StyledTableCell align="right">Apto</StyledTableCell>
            <StyledTableCell align="right">Operador</StyledTableCell>
            <StyledTableCell align="right">Retirada</StyledTableCell>
            <StyledTableCell align="right">Morador</StyledTableCell>
            <StyledTableCell align="right"></StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {deliveries && deliveries.map((row) => (
            <TableRow key={row.deliveryCode} hover>
              <StyledTableCell component="th" scope="row" id={`delivery_${row.id}`}>{row.deliveryCode}</StyledTableCell>
              <StyledTableCell align="right">{Moment(row.registerDate).format('DD/MM/YYY HH:MM')}</StyledTableCell>
              <StyledTableCell align="right">{row.description}</StyledTableCell>
              <StyledTableCell align="right">{row.apartment}</StyledTableCell>
              <StyledTableCell align="right">{row.operatorName}</StyledTableCell>
              <StyledTableCell align="right" id={`withdrawal_date${row.id}`}>{row.withdrawalDate && Moment(row.withdrawalDate).format('DD/MM/YYY HH:MM')}</StyledTableCell>
              <StyledTableCell align="right">{row.residentName}</StyledTableCell>
              <StyledTableCell align="right">
                {
                  !row.withdrawalDate && <Icon id={row.deliveryCode} icon={"far fa-edit"} title={"Registrar retirada"} color={"red"} onClick={onClick.bind(this)} />
                }
              </StyledTableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
