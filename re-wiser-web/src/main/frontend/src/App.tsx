import React from 'react';
import './App.css';
import {DataGrid, GridColDef} from '@mui/x-data-grid';


const columns: GridColDef[] = [
    {field: 'id', headerName: 'ID', width: 70},
    {field: 'from', headerName: 'From', width: 230},
    {field: 'to', headerName: 'To', width: 230},
    {field: 'subject', headerName: 'Subject', width: 200,},
    {field: 'body', headerName: 'Message', width: 90,},
    {field: 'attachments', headerName: 'Attachments', width: 100}
    /*{
        field: 'fullName',
        headerName: 'Full name',
        description: 'This column has a value getter and is not sortable.',
        sortable: false,
        width: 160,
        valueGetter: (params: GridValueGetterParams) =>
            `${params.getValue(params.id, 'to') || ''} ${
                params.getValue(params.id, 'from') || ''
            }`,
    },*/
];

const rows = [
    {id: 1, from: 'Snow', to: 'Jon', subject: 'subject', body: 'body', attachments: 'attachments'},
    {id: 2, from: 'Snow', to: 'Jon', subject: 'subject', body: 'body', attachments: 'attachments'},
    {id: 3, from: 'Snow', to: 'Jon', subject: 'subject', body: 'body', attachments: 'attachments'},
    {id: 4, from: 'Snow', to: 'Jon', subject: 'subject', body: 'body', attachments: 'attachments'},
    {id: 5, from: 'Snow', to: 'Jon', subject: 'subject', body: 'body', attachments: 'attachments'},
    {id: 6, from: 'Snow', to: 'Jon', subject: 'subject', body: 'body', attachments: 'attachments'},
    {id: 7, from: 'Snow', to: 'Jon', subject: 'subject', body: 'body', attachments: 'attachments'},
    {id: 8, from: 'Snow', to: 'Jon', subject: 'subject', body: 'body', attachments: 'attachments'},
    {id: 9, from: 'Snow', to: 'Jon', subject: 'subject', body: 'body', attachments: 'attachments'},
];


function App() {
    return (
        <div className="App">
            <div style={{height: 400, width: '100%'}}>

                <DataGrid
                    rows={rows}
                    columns={columns}
                    pageSize={5}
                    rowsPerPageOptions={[5]}
                    checkboxSelection
                />
            </div>
        </div>
    );
}

export default App;
