/*
 * @Author       : nonameless
 * @Date         : 2020-10-06 20:47:17
 * @LastEditors  : nonameless
 * @LastEditTime : 2020-12-11 01:24:44
 */
import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import "./assets/css/global.css";
import globalVue from "./components/Global";
import md5 from "js-md5";
import qs from "qs";

Vue.config.productionTip = false;
Vue.prototype.global = globalVue
Vue.prototype.$qs = qs
Vue.use(ElementUI);
Vue.prototype.$md5 = md5;

// 请求拦截
axios.interceptors.request.use(config => {
    // console.log(config)
    config.headers.token = window.sessionStorage.getItem('token')
    // 最后必须return config
    return config
})

axios.interceptors.response.use(
    result => {
        const data = result.data
        if (data.code === '401') {
            this.$router.push('/user/login')
            Vue.prototype.$message.error(data.msg)
        }
        if (result.headers.token !== null) {
            window.sessionStorage.setItem("token", result.headers.token);
        }
        return result
    },
    error => {
        console.log(error.response);
    }
)

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
