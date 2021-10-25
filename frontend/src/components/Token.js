import { removeCookie } from "./Cookie";
import axios from 'axios';

export const refreshToken = () =>{
    console.log('call refreshtoken');
    const api = axios.create({
      baseURL: 'http://localhost:8080'
    })
    api.post('/token/refresh', {
        headers: {
          'Authorization':"Bearer "+localStorage.getItem('refresh-token'),
        }
      }).then(function (response) {
        console.log(response.data);
        localStorage.removeItem('refresh-token');
        removeCookie('access-token');
        removeCookie('user-name');

        localStorage.setItem('refresh-token',response.data['refresh-token']);
        setCookie('access-token',response.data['access-token']);
        setCookie('user-name',userId);
        return 1;
      }).catch(function (error) {
        console.log(error);
      });
}