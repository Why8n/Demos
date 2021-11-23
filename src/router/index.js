// file: router/index.js
import { createRouter, createWebHistory } from 'vue-router';

export default createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      // 主页面同时显示多个路由映射组件
      components: {
        // Main.vue 显示到 default 视图上
        default: () => import('@/components/named_view/Main.vue'),
        // SideBar.vue 显示到 sidebar 视图上
        sidebar: () => import('@/components/named_view/SideBar.vue'),
      },
    },
  ],
});
