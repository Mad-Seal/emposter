import axios from "axios";
import {Rows} from "./Rows";

const baseUrl = 'http://localhost:8080/wiser/messages'

const request = axios.create({baseURL: baseUrl});

const GetMessages = () => {
    return request.get<Array<Rows>>('');
}

export {GetMessages};