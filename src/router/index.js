// file: router/index.js
import { createRouter, createWebHistory } from 'vue-router';

export default createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/user',
      component: () => import('@/components/User.vue'),
      props: (route) => ({ query: route.query.name }),
    },
  ],
});

