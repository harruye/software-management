import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import axios from 'axios'


const app=createApp(App)
app.use(store).use(router).use(ElementPlus).mount('#app')
app.config.globalProperties.$http=axios



