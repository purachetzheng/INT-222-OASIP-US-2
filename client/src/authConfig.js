import { PublicClientApplication } from '@azure/msal-browser'
const msalConfig = {
    auth: {
        clientId: 'd3ed05f4-9759-4fab-b56c-18876a098ddc',
        authority:
            'https://login.microsoftonline.com/6f4432dc-20d2-441d-b1db-ac3380ba633d',
        // redirectUri: '/',
        redirectUri: 'http://localhost:4000/us2/',
        // redirectUri: 'https://intproj21.sit.kmutt.ac.th/us2/authentication',
        // redirectUri: 'https://intproj21.sit.kmutt.ac.th/us2/authentication',
        postLogoutRedirectUri: '/' // Must be registered as a SPA redirectURI on your app registration
    },
    cache: {
        cacheLocation: 'localStorage', // Options are localStorage, sessionStorage, memoryStorage
    },
    
}

export const msalInstance = new PublicClientApplication(msalConfig)

// Add here scopes for id token to be used at MS Identity Platform endpoints.
export const loginRequest = {
    scopes: ['User.Read'],
}

// Add here the endpoints for MS Graph API services you would like to use.
export const graphConfig = {
    // graphMeEndpoint: 'https://graph.microsoft.com/v1.0/me',
    graphMeEndpoint: 'https://graph.microsoft.com/User.Read',
}
