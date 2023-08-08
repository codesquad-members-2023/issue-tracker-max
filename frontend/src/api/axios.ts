import axios from 'axios';
const BASE_URL = import.meta.env.DEV
  ? 'http://ec2-43-202-134-94.ap-northeast-2.compute.amazonaws.com:8080'
  : import.meta.env.VITE_API_URL;

export default axios.create({
  baseURL: BASE_URL,
});

export const axiosPrivate = axios.create({
  baseURL: BASE_URL,
  headers: { 'Content-Type': 'application/json' },
  withCredentials: true,
});
