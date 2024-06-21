import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/Home.vue')
  },
  {
    path: '/my-videos',
    name: 'MyVideo',
    component: () => import('@/views/MyVideo.vue'),
  },
  {
    path: '/Register',
    name: 'Register',
    component: () => import('@/components/Register.vue')
  },
  {
    path: '/mine',
    name: 'Mine',
    component:()=> import('@/components/Mine.vue')
  },
  {
    path: '/Upload',
    name: 'Upload',
    component: () => import('@/views/Upload.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/components/Login.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router