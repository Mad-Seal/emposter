import {Attachment} from "./Attachment";

class Email {
    id: number;
    from: string;
    to: string;
    cc: string;
    bcc: string;
    subject: string;
    text: string;
    receivedDateTime: string;
    attachments: Array<Attachment>;

    constructor(id: number,
                from: string,
                to: string,
                cc: string,
                bcc: string,
                subject: string,
                text: string,
                receivedDateTime: string,
                attachments: Array<Attachment>) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.text = text;
        this.receivedDateTime = receivedDateTime;
        this.attachments = attachments;
    }
}

export {Email}