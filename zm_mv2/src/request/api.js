import {get, post} from './http'
export const list = (page, limit) => get(
  `http://localhost:8080/springbootb9wc1/videoinfo/list`, {
    page: page,
    limit: limit
  }
);
export const like = (v2) => post(`http://localhost:8080/springbootb9wc1/videoinfo/like/${v2}`);
export const pl = (v3, v4) => get(`https://www.maomin.club/muc/comment/mv?id=${v3}&limit=${v4}`, v3)
