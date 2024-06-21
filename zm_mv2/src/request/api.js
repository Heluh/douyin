import service from "@request/http";

// 定义各个API接口函数
export const list = (page, limit) => service.get(
  '/springbootb9wc1/videoinfo/list',
  {
  params: {
    page: page,
    limit: limit
  }
});

export const mylist = (page, limit) => service.get(
  '/springbootb9wc1/videoinfo/mylist',
  {
  params: {
    page: page,
    limit: limit
  }
});

//获取本人信息
export const myinfo = () => service.get(
  '/springbootb9wc1/user'
);

export const like = (v2) => service.post(
  `/springbootb9wc1/videoinfo/like/${v2}`
);

//用户是否点赞
export const islike = (v2) => service.get(
  `/springbootb9wc1/videoinfo/islike/${v2}`
);

export const pl = (v3, v4) => service.get(
  'springbootb9wc1/comment/mv',
  {
  params: {
    id: v3,
    limit: v4
  }
});

export const register = (user) => service.post(
  '/springbootb9wc1/user/register',
  user
);

export const login = (userName, password) => service.post(
  '/springbootb9wc1/user/login',
  null,
  {
  params: {
    username: userName,
    password: password
  }
});

export const recordVideoWatch = (videoId) => service.post(
    `/springbootb9wc1/videoinfo/click/${videoId}`,
    null,
    {
    }
);

export const upload = (formData) => service.post(
    '/springbootb9wc1/videoinfo/add',
    formData,
    {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    }
    );