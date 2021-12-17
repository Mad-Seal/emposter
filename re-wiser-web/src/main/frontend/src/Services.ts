import axios from "axios";
import {Email} from "./Email";

const baseUrl = 'http://localhost:8080/wiser/messages'

const request = axios.create({baseURL: baseUrl});

const GetMessages = () => {
    return request.get<Array<Email>>('');
}

const GetCanPurge = () => {
    return new Promise<boolean>((resolve, reject) => {
        resolve(true)
    })
}

const Purge = () => {

}

export {GetMessages, GetCanPurge, Purge};