import Vue from 'vue'
import App from './App.vue'
import router from './router'
import { Swipe, SwipeItem, ActionSheet, Icon, List,Field,Button,NavBar,Uploader } from 'vant';
import 'vant/lib/index.css';
import 'swiper/dist/css/swiper.min.css'
import 'swiper/dist/js/swiper.min'


Vue.use(Swipe).use(SwipeItem).use(ActionSheet).use(Icon).use(List).use(Field).use(Button)
    .use(NavBar).use(Uploader);
Vue.config.productionTip = false


new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
