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

export default function TableResidents(props) {
  const classes = useStyles();
  const { residents, onClick } = props;

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table} aria-label="customized table">
        <TableHead>
          <TableRow>
            <TableCell align="left">Nome</TableCell>
            <TableCell align="left">RG</TableCell>
            <TableCell align="left">Apartamento</TableCell>
            <TableCell align="left"></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {residents && residents.map((row) => (
            <TableRow key={row.id} hover>
              <TableCell component="th" scope="row" id={`resident_${row.id}`}>{row.name}</TableCell>
              <TableCell align="left">{row.rg}</TableCell>
              <TableCell align="left">{row.apartment}</TableCell>
              <TableCell align="right">
                {
                  !row.deactivationDate && <Icon id={row.id} icon={"fas fa-minus-square"} title={"Desativar morador"} color={"red"} onClick={onClick.bind(this)} />
                }
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
