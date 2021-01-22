/*
 * @Author       : nonameless
 * @Date         : 2020-10-06 20:47:17
 * @LastEditors  : nonameless
 * @LastEditTime : 2020-12-22 17:51:57
 */
import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
ElementUI.Dialog.props.lockScroll.default = false;
import 'element-ui/lib/theme-chalk/index.css';
import "./assets/css/global.css";
import globalVue from "./components/Global";
import md5 from "js-md5";
import qs from "qs";
import "./assets/icon/iconfont.css"

import VueCodeMirror from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
Vue.use(VueCodeMirror)


Vue.config.productionTip = false;
Vue.prototype.global = globalVue
Vue.prototype.$qs = qs
Vue.use(ElementUI);
Vue.prototype.$md5 = md5;

// 引入vue-json-excel
import JsonExcel from 'vue-json-excel'

Vue.component('downloadExcel', JsonExcel)


axios.interceptors.request.use(config => {
    // console.log(config)
    config.headers.token = window.localStorage.getItem('token')
    // 最后必须return config
    return config
})

axios.interceptors.response.use(result => {
    
    const data = result.data
    if (result.data.code === 401) {
        // this.$message.error("无权限，请登录后访问")
        // this.$router.push('/user/login')
        // Vue.prototype.$message.error(data.msg)
        Vue.prototype.$message.error("非授权访问")
        router.push('/user/login')
    }
    window.localStorage.setItem("token", result.headers.token);
    return result
},
error => {
    if(error.response.data.code == 401){
        Vue.prototype.$message.error("无权限，请登录后访问")
        router.push('/user/login')
    }
}
)



new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
