import axios from 'axios';

// 创建axios实例
const service = axios.create({
    baseURL: 'http://localhost:8080', // api的base_url
    timeout: 5000 // 请求超时时间
});

// request拦截器
service.interceptors.request.use(
  config => {
      const token = localStorage.getItem('token');
      if (token) {
          config.headers['Authorization'] = 'Bearer ' + token; // 让每个请求携带自定义token
      }
      return config;
  },
  error => {
      // Do something with request error
      console.log(error); // for debug
      Promise.reject(error);
  }
);

// response拦截器
service.interceptors.response.use(
  response => {
    // 在这里你可以对响应数据做一些处理
    return response;
  },
  error => {
    // 在这里你可以对返回的错误进行一些处理
    if (error.response && error.response.status === 401) { // 假设401表示token失效
      console.log("Token is invalid. Please login again."); // for debug
      localStorage.removeItem('token'); // 清除无效的token
    }
    console.log(error); // for debug
    return Promise.reject(error);
  }
);

export default service;