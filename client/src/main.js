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
import AppButton from './components/App/AppButton/AppButton.vue'
import AppInput from './components/App/AppInput.vue'
import AppVeeTextArea from './components/App/AppVeeTextArea.vue'
import AppVeeInput from './components/App/AppVeeInput.vue'
import AppLoadingScreen from './components/App/AppLoadingScreen.vue'
// import AppInput from './components/App/AppInput.vue'

import { msalPlugin } from './services/MSAL/plugins/msalPlugin'
import { msalInstance } from './authConfig'
const pinia = createPinia()
const app = createApp(App)

app
  .component('PrimaryButton', PrimaryButton)
  .component('SecondaryButton', SecondaryButton)
  .component('app-modal', AppModal)
  .component('app-button', AppButton)
  .component('app-input', AppInput)
  .component('app-vee-input', AppVeeInput)
  .component('app-vee-textarea', AppVeeTextArea)
  .component('app-loading-screen', AppLoadingScreen)

fontawesome.init(app)
app.use(pinia)
app.use(router)
app.use(i18n)
app.use(msalPlugin, msalInstance);
app.mount('#app')
