import { createApp } from 'vue'
import './styles/index.css'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
import i18n from './locales/index'
import fontawesome from './assets/fontawesome'
import PrimaryButton from './components/base/BaseButton/PrimaryButton.vue'
import SecondaryButton from './components/base/BaseButton/SecondaryButton.vue'
import AppModal from './components/App/AppModal.vue'
import AppButton from './components/App/AppButton.vue'
const pinia = createPinia()
const app = createApp(App)

app
  .component('PrimaryButton', PrimaryButton)
  .component('SecondaryButton', SecondaryButton)
  .component('app-modal', AppModal)
  .component('app-button', AppButton)

fontawesome.init(app)
app.use(pinia)
app.use(router)
app.use(i18n)
app.mount('#app')
