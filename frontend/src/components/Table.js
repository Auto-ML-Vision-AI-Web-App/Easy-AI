import * as React from 'react';
import { DataGrid } from '@mui/x-data-grid';

import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

const checkColumns = [
    { field: 'id', headerName: 'ID' },
    { field: 'model', headerName: 'Model', width:180},
    { field: 'owner', headerName: 'Owner' },
    { field: 'accuracy', headerName: '정확도', type: 'number' },
    { field: 'loss', headerName: '손실값', type: 'number' },
    { field: 'label', headerName: 'Label', width:200},
];


export function CheckTable(props) {
    //const [rows, setRows] = React.useState(props.rows)
    //console.log(props.rows)
    return (
        <div style={{ height: '300px', width: '100%' }}>
            <DataGrid
                rows={props.rows}
                columns={checkColumns}
                pageSize={10}
                rowHeight={35}
                headerHeight={40}
                checkboxSelection
                disableSelectionOnClick
            />
        </div>
    );
}

const useStyles = makeStyles({
    tableDiv:{
        marginTop: 20,
        marginBottom: 10
    },
    table: {
        minWidth: 650,
        backgroundColor: '#e6f5f7'
    },
});


export function ModelTable(props) {
    const classes = useStyles();
    
    function createData(id, type, accuracy, loss) {
        return { id, type, accuracy, loss };
    }
    
    const modelRows = [
        createData(0, props.type, props.final_accuracy, props.final_loss),
    ];

    return (
        <TableContainer className={classes.tableDiv} component={Paper}>
            <Table className={classes.table} aria-label="simple table">
                <TableHead style={{color:'white'}}>
                    <TableRow>
                        <TableCell><strong>모델 ID</strong></TableCell>
                        <TableCell><strong>모델 종류</strong></TableCell>
                        <TableCell><strong>정확도</strong></TableCell>
                        <TableCell><strong>손실값</strong></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {modelRows.map((row) => (
                        <TableRow key={row.id}>
                            <TableCell component="th" scope="row">
                                {row.id}
                            </TableCell>
                            <TableCell component="th">{row.type}</TableCell>
                            <TableCell component="th">{row.accuracy}</TableCell>
                            <TableCell component="th">{row.loss}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}

