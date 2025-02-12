import axios from 'axios'

const APIURL = import.meta.env.VITE_APIURL

const apiClient = axios.create({
  baseURL: APIURL, //"http://localhost:8080/api",
  headers: {
    'Content-type': 'application/json'
  }
})

export default apiClient