import { createRouter, createWebHistory } from 'vue-router';

const routes = [
  {
    path: '/news',
    component: () => import('@/components/nested_router/News.vue'),
    // 配置嵌套路由
    children: [
      {
        // /news 重定向到 /news/finance
        path: '/news',
        redirect: '/news/finance',
      },
      {
        // /news/finace
        path: 'finance',
        component: () => import('@/components/nested_router/Finance.vue'),
      },
      {
        // /news/sports
        path: 'sports',
        component: () => import('@/components/nested_router/Sports.vue'),
      },
    ],
  },
];

export default createRouter({
  // 使用 History 模式
  history: createWebHistory(),
  routes,
});
