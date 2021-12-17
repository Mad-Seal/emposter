import React, {useEffect, useState} from 'react';
import './App.css';
import CollapsibleTable from "./CollapsibleTable";
import {GetCanPurge, GetMessages, Purge} from "./Services";
import {Button} from "@mui/material";
import {Email} from "./Email";

function App() {

    const [emails, setEmails] = useState<Email[]>([]);
    const [canPurge, setCanPurge] = useState(false);

    useEffect(() => {
        GetCanPurge()
            .then(canPurge => {
                setCanPurge(canPurge)
            })
    })

    const onClick = () => {
        GetMessages()
            .then(({data}) => {
                setEmails(data);
            })
    }

    return (
        <div className="App">
            <Button onClick={() => onClick()}>Refresh</Button>
            <Button disabled={!canPurge} onClick={() => Purge()}>Purge</Button>
            <div style={{height: 400, width: '90%', margin: '5%'}}>
                <CollapsibleTable rows={emails}/>
            </div>
        </div>
    );
}

export default App;
