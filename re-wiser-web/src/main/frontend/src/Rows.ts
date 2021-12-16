import {Attachment} from "./Attachment";

class Rows {
    id: number;
    from: string;
    to: string;
    subject: string;
    message: string;
    date: string;
    attachments: Array<Attachment>;

    constructor(id: number, from: string, to: string, subject: string, message: string, date: string, attachments: Array<Attachment>) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.message = message;
        this.date = date;
        this.attachments = attachments;
    }
}

export {Rows}