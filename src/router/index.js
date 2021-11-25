// file: router/index.js
import { createRouter, createWebHistory } from 'vue-router';

import User from '@/components/User.vue';
export default createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/user_one',
      //   alias: '/user_1',
      alias: ['/user_1', '/user_yi'],
      component: User,
    },
  ],
});
