// 在这里全局导入一些库，比如element-ui，axios等
import axios from 'axios'
import ElementUI from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
import "font-awesome/css/font-awesome.min.css"
import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import './mock'
import Routes from './router/index'

Vue.use(VueRouter);
Vue.use(ElementUI); //全局注册elementUI
Vue.prototype.$http = axios
const router = new VueRouter({
    routes: Routes,
    mode: 'history'
});

new Vue({
    render: h => h(App),
    router: router,
}).$mount('#app')