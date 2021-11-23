import * as React from 'react';
import { DataGrid } from '@mui/x-data-grid';

const columns = [
    { field: 'id', headerName: 'ID' },
    { field: 'model', headerName: 'Model' },
    { field: 'owner', headerName: 'Owner' },
    { field: 'accuracy', headerName: 'Accuracy', type: 'number' },
    { field: 'loss', headerName: 'Loss', type: 'number' },
];


export default function DataTable(props) {
    //const [rows, setRows] = React.useState(props.rows)
    //console.log(props.rows)
    return (
        <div style={{ height: '300px', width: '100%' }}>
            <DataGrid
                rows={props.rows}
                columns={columns}
                pageSize={5}
                rowHeight={35}
                headerHeight={40}
                checkboxSelection
                disableSelectionOnClick
            />
        </div>
    );
}
