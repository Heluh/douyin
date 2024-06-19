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
  }
]

const router = new VueRouter({
  routes
})

export default router