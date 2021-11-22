<template>
  <!-- 模板获取 route.params 属性 -->
  <h1>User Component: {{ $route.params }}</h1>
  <h2>hhUser Component: {{ $router.currentRoute }}</h2>
</template>

<script>
import { onBeforeRouteUpdate, useRoute, useRouter } from 'vue-router';

export default {
  name: 'User',
  setup(props, context) {
    // Router 钩子函数
      const route = useRoute();
      const router = useRouter();

    onBeforeRouteUpdate((to, from, next) => {
      // 代码获取 route.params 属性
      console.log(to);
      next();
        
        console.log('-------------------')
        console.log(route);
        console.log('###################')
        console.log(router.currentRoute.value);
    });
  },
};
</script>