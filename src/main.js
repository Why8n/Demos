import { createApp } from 'vue';
import App from './App.vue';

import router from './router/index.js';

const app = createApp(App);
// 装载 Vue Router 实例，确保整个 Vue 应用全局支持路由
app.use(router);
app.mount('#app');
