import React, {useState} from 'react';
import './App.css';
import CollapsibleTable from "./CollapsibleTable";
import {GetMessages} from "./Services";
import {Button} from "@mui/material";
import {Rows} from "./Rows";
import {Attachment} from "./Attachment";

function App() {

    const createData = (
        id: number,
        from: string,
        to: string,
        subject: string,
        message: string,
        date: string,
        attachments: Array<Attachment>
    ) => {
        return {
            id,
            from,
            to,
            subject,
            message,
            date,
            attachments
        };
    }

    const attachments =
        [
            {
                name: 'important file 1',
                id: 11091700,
            },
            {
                name: 'important file 1',
                id: 23452,
            },
        ];


    let rows: Array<Rows> = [
        createData(1, 'from', 'to', 'subject', 'message', 'today', attachments),
        createData(2, 'from', 'to', 'subject', 'message', 'today', attachments),
        createData(3, 'from', 'to', 'subject', 'message', 'today', attachments),
    ];
    const [messages, setMessages] = useState<any>([]);

    const onClick = () => {
        GetMessages()
            .then(({data}) => {
                setMessages(data);
                // rows = [createData(1, '', '', '', messages, '', attachments)]
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
