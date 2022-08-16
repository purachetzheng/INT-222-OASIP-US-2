import { createApp } from 'vue'
import './styles/index.css'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
import i18n from './locales/index'
import fontawesome from './assets/fontawesome'


const pinia = createPinia()
const app = createApp(App)

fontawesome.init(app)
app.use(pinia)
app.use(router)
app.use(i18n)
app.mount('#app')
