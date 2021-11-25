<template>
  <h1>Home Component</h1>
</template>

<script>
import { onBeforeRouteLeave, onBeforeRouteUpdate } from 'vue-router';
export default {
  name: 'Home',

  setup(props, context) {
    console.log('setup = onBeforeRouteEnter');
    // update 守卫确认导航跳转，因此没有 next 参数
    onBeforeRouteUpdate((fto, from) => {
      console.log('onBeforeRouteUpdate');
    });

    // leave 守卫同样没有 next 参数
    onBeforeRouteLeave((to, from) => {
      console.log('onBeforeRouteLeave');
    });
  },
};
</script>

<style scoped>
h1 {
  background-color: green;
}
</style>