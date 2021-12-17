import React, {useState} from 'react';
import './App.css';
import CollapsibleTable from "./CollapsibleTable";
import {GetMessages} from "./Services";
import {Button} from "@mui/material";
import {Rows} from "./Rows";

function App() {

    const [messages, setMessages] = useState<Rows[]>([]);

    const onClick = () => {
        GetMessages()
            .then(({data}) => {
                setMessages(data);
            })
    }
    return (
        <div className="App">
            <Button onClick={() => onClick()}>Refresh</Button>
            <div style={{height: 400, width: '90%', margin: '5%'}}>
                <CollapsibleTable rows={messages}/>
            </div>
        </div>
    );
}

export default App;
