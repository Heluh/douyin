import {get, post} from './http'
export const list = (page, limit) => get(
  `http://localhost:8080/springbootb9wc1/videoinfo/list`, {
    page: page,
    limit: limit
  }
);

export const mylist = (page, limit) => get(
  `http://localhost:8080/springbootb9wc1/videoinfo/mylist`, {
    page: page,
    limit: limit
  }
);


export const like = (v2) => post(`http://localhost:8080/springbootb9wc1/videoinfo/like/${v2}`);
export const pl = (v3, v4) => get(`https://www.maomin.club/muc/comment/mv?id=${v3}&limit=${v4}`, v3)
export const register = (user) => post(`http://localhost:8080/springbootb9wc1/user/register`, user);
export const login = (userName,password) => post(`http://localhost:8080/springbootb9wc1/user/login?username=${userName}&password=${password}`);