import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './Vuex/store'
// import store from './store'
import axios from 'axios'
import VueSidebarMenu from 'vue-sidebar-menu'
import 'vue-sidebar-menu/dist/vue-sidebar-menu.css'
import './assets/common.css'

const app = createApp(App)
app.config.globalProperties.$axios = axios;  //전역변수로 설정 컴포넌트에서 this.$axios 호출할 수 있음
app.config.globalProperties.$serverUrl = '//13.125.49.195:8080' //api server
app.config.globalProperties.$store = store

app.use(router)
app.use(store)

app.use(VueSidebarMenu)
app.mount('#app')
