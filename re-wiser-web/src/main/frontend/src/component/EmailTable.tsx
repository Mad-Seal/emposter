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
import {Email} from "../model/Email";
import {Link} from "@mui/material";


function Row(props: { email: Email }) {
    const {email} = props;
    const [expanded, setExpanded] = React.useState(false);

    return (
        <React.Fragment>
            <TableRow sx={{'& > *': {borderBottom: 'unset'}}}>
                <TableCell>
                    <IconButton
                        aria-label="expand row"
                        size="small"
                        onClick={() => setExpanded(!expanded)}
                    >
                        {expanded ? <KeyboardArrowUpIcon/> : <KeyboardArrowDownIcon/>}
                    </IconButton>
                </TableCell>
                <TableCell align="right">{email.id}</TableCell>
                <TableCell align="right">{email.from}</TableCell>
                <TableCell align="right">{email.to}</TableCell>
                <TableCell align="right">{email.cc}</TableCell>
                <TableCell align="right">{email.bcc}</TableCell>
                <TableCell align="right">{email.subject}</TableCell>
                <TableCell align="right">{email.receivedDateTime}</TableCell>
            </TableRow>
            <TableRow>
                <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={6}>
                    <Collapse in={expanded} timeout="auto" unmountOnExit>
                        <Box sx={{margin: 1}}>
                            <Typography variant="h6" gutterBottom component="div">
                                Email text
                            </Typography>
                            <Typography variant="body2" gutterBottom component="div">
                                {/*TODO: check of potential XSS when message contain html and js*/}
                                {email.message}
                            </Typography>
                            <Typography variant="h6" gutterBottom component="div">
                                Attachments
                            </Typography>
                            <Table size="small" aria-label="purchases">
                                <TableBody>
                                    {email.attachments.map((attachment) => (
                                        <TableRow key={attachment.id}>
                                            <TableCell component="th" scope="row">
                                                <Link href={"attachment/" + attachment.id}>{attachment.name}</Link>
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


export default function EmailTable(props: { emails: Email[] }) {
    return (
        <TableContainer component={Paper}>
            <Table aria-label="email table">
                <TableHead>
                    <TableRow>
                        <TableCell/>
                        <TableCell align="right">Id</TableCell>
                        <TableCell align="right">From</TableCell>
                        <TableCell align="right">To</TableCell>
                        <TableCell align="right">Cc</TableCell>
                        <TableCell align="right">Bcc</TableCell>
                        <TableCell align="right">Subject</TableCell>
                        <TableCell align="right">Date</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {props.emails.map((email) => (
                        <Row key={email.id} email={email}/>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
