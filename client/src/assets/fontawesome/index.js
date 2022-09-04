
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon, FontAwesomeLayers, FontAwesomeLayersText } from '@fortawesome/vue-fontawesome'
import solidIconsList from './free-solid'
import brandsIconsList from './free-brands'
import regularIconsList from './free-regular'

const fontawesome = {
    init(app){
        library.add(...solidIconsList, ...brandsIconsList, ...regularIconsList)
        app.component('fa-icon', FontAwesomeIcon)
        app.component('fa-layer', FontAwesomeLayers)
        app.component('fa-layer-text', FontAwesomeLayersText)
    }
}
export default fontawesome