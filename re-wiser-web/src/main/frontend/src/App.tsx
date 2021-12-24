import React, {useEffect, useState} from 'react';
import './App.css';
import EmailTable from "./component/EmailTable";
import {GetCanPurge, GetMessages, Purge} from "./service/ApiService";
import {Button} from "@mui/material";
import {Email} from "./model/Email";
import Alert from "@mui/material/Alert";

function App() {

    const [emails, setEmails] = useState<Email[]>([]);
    const [canPurge, setCanPurge] = useState<boolean>(false);

    useEffect(() => {
        GetCanPurge()
            .then(({data}) => {
                setCanPurge(data)
            })
    })

    const onClick = () => {
        GetMessages()
            .then(({data}) => {
                setEmails(data);
            })
    }

    let alert = "Backend which you are using does not (fully or partially) support purging.";
    return (
        <div className="App">
            {!canPurge && <Alert severity="warning">{alert}</Alert>}
            <Button onClick={() => onClick()}>Refresh</Button>
            <Button onClick={() => Purge()}>Purge</Button>
            <div style={{height: 400, width: '90%', margin: '5%'}}>
                <EmailTable emails={emails}/>
            </div>
        </div>
    );
}

export default App;
