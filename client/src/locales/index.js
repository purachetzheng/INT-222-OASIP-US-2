import { createI18n } from 'vue-i18n'
import en from './en.json'
import th from './th.json'
const i18n = createI18n({
    locale: "en",
    messages: {
        en: en,
        th: th
    }
})

export default i18n;