import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import Icon from '../../Icon';

import '../style.css';

const useStyles = makeStyles({
  table: {
    minWidth: 700,
  },
});

export default function TableOperators(props) {
  const classes = useStyles();
  const { operators, onClick } = props;

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table} aria-label="customized table">
        <TableHead>
          <TableRow>
            <TableCell align="left">Primeiro Nome</TableCell>
            <TableCell align="left">Último Nome</TableCell>
            <TableCell align="left"></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {operators && operators.map((row) => (
            <TableRow key={row.id} hover>
              <TableCell component="th" scope="row" id={`operator_${row.id}`}>{row.firstName}</TableCell>
              <TableCell align="left">{row.lastName}</TableCell>
              <TableCell align="right">
                <Icon id={row.id} icon={"fas fa-minus-square"} title={"Deletar operador"} color={"red"} onClick={onClick.bind(this)} />
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
