import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router';

import routes from './routes';

const history = createWebHistory('/us2/');

const router = createRouter({ history, routes });
export default router;
