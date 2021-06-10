import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';

// Generate Order Data
function createData(id, primary_id, name, type, date) {
  const link_string = "프로젝트 바로가기";
  return { id, primary_id, name, type, date, link_string };
}

const rows = [
  createData(0, '0', '나의 첫 AI', '이미지 분류하기', '2021.05.15'),
  createData(1, '1', '옷 카테고리 판별기', '이미지 분류하기', '2021.05.28'),
  createData(2, '2', '옷 색 판별기', '물체 탐지하기', '2021.06.09'),
];

function preventDefault(event) {
  event.preventDefault();
}

const useStyles = makeStyles((theme) => ({
  seeMore: {
    marginTop: theme.spacing(3),
  },
}));

export default function Orders() {
  const classes = useStyles();
  return (
    <React.Fragment>
      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>Project ID</TableCell>
            <TableCell>Project Name</TableCell>
            <TableCell>AI Type</TableCell>
            <TableCell>Created Date</TableCell>
            <TableCell>Project Link</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow key={row.id}>
              <TableCell>{row.primary_id}</TableCell>
              <TableCell>{row.name}</TableCell>
              <TableCell>{row.type}</TableCell>
              <TableCell>{row.date}</TableCell>
              <TableCell><a style={{color:"#08AAC1"}} href={row.link_string}><strong>{row.link_string}</strong></a></TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </React.Fragment>
  );
}