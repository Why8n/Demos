// file: router/index.js
import { createRouter, createWebHistory } from 'vue-router';

export default createRouter({
  history: createWebHistory(),
  routes: [
    {
      name: 'Home',
      path: '/home',
      component: () => import('@/components/Home.vue'),
    },
    {
      name: 'Me',
      path: '/me',
      component: () => import('@/components/Me.vue'),
    },
  ],
});
