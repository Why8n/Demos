// file: router/index.js
import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/home/:id',
      component: () => import('@/components/Home.vue'),
      // 支持传递多个钩子函数
      // beforeEnter: [(..)=>{..}, (..)=>{..}],
    //   beforeEnter: (to, from, next) => {
    //     console.log('Home: Route beforeEnter');
    //     next();
    //   },
    },
    {
      path: '/me',
      component: () => import('@/components/Me.vue'),
    //   beforeEnter: (to, from) => {
    //     console.log('Me: Route beforeEnter');
    //     return true;
    //   },
    },
    { path: '/user', component: () => import('@/components/User.vue') },
  ],
});

// router.beforeEach((to, from) => {
//   console.log('Router beforeEach 1');
//   if(to.path === '/user')
//       return true;
//   return '/user';
// });

// router.beforeEach((to, from, next) => {
//   console.log('Router beforeEach 2');
//   next();
// });

// router.onError((error, to, from) => {
//   console.log(error, to, from);
// });

// router.beforeResolve((to, from, next) => {
//   console.log('Router beforeResolve');
// });

// router.afterEach((to, from, failure) => {
//   console.log('Router afterEach');
// });

export default router;
