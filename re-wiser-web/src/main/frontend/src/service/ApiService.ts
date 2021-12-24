import axios from "axios";
import {Email} from "../model/Email";

const baseUrl = '/wiser/messages'

const request = axios.create({baseURL: baseUrl});

const GetMessages = () => {
    return request.get<Array<Email>>('');
}

const GetCanPurge = () => {
    return request.get<boolean>('purgable')
}

const Purge = () => {
    return request.delete('')
}

export {GetMessages, GetCanPurge, Purge};