import * as React from 'react';
import Box from '@mui/material/Box';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import {Email} from "./Email";


function Row(props: { row: Email }) {
    const {row} = props;
    const [open, setOpen] = React.useState(false);

    return (
        <React.Fragment>
            <TableRow sx={{'& > *': {borderBottom: 'unset'}}}>
                <TableCell>
                    <IconButton
                        aria-label="expand row"
                        size="small"
                        onClick={() => setOpen(!open)}
                    >
                        {open ? <KeyboardArrowUpIcon/> : <KeyboardArrowDownIcon/>}
                    </IconButton>
                </TableCell>
                <TableCell align="right">{row.id}</TableCell>
                <TableCell align="right">{row.from}</TableCell>
                <TableCell align="right">{row.to}</TableCell>
                <TableCell align="right">{row.subject}</TableCell>
                <TableCell align="right">{row.receivedDateTime}</TableCell>
            </TableRow>
            <TableRow>
                <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={6}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Box sx={{margin: 1}}>
                            <Typography variant="h6" gutterBottom component="div">
                                Message
                            </Typography>
                            <Typography variant="body2" gutterBottom component="div">
                                {row.message}
                            </Typography>
                            <Typography variant="h6" gutterBottom component="div">
                                Attachments
                            </Typography>
                            <Table size="small" aria-label="purchases">
                                <TableBody>
                                    {row.attachments.map((attachment) => (
                                        <TableRow key={attachment.id}>
                                            <TableCell component="th" scope="row">
                                                {attachment.name}
                                            </TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </Box>
                    </Collapse>
                </TableCell>
            </TableRow>
        </React.Fragment>
    );
}


export default function CollapsibleTable(props: { rows: Array<Email> }) {
    return (
        <TableContainer component={Paper}>
            <Table aria-label="collapsible table">
                <TableHead>
                    <TableRow>
                        <TableCell style={{width: "5%"}}/>
                        <TableCell style={{width: "10%"}} align="right">Id</TableCell>
                        <TableCell style={{width: "15%"}} align="right">From</TableCell>
                        <TableCell style={{width: "20%"}} align="right">To</TableCell>
                        <TableCell style={{width: "40%"}} align="right">Subject</TableCell>
                        <TableCell style={{width: "10%"}} align="right">Date</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {props.rows.map((row) => (
                        <Row key={row.id} row={row}/>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
