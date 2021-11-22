<template>
  <h1>Main Page</h1>
  <!-- <div class="nav"> -->
    <!-- router-link 最终会被渲染为一个 a 标签 -->
    <!-- <router-link to="/home">Home</router-link>
    <router-link to="/me">Me</router-link>
    <router-link :to="'/user/' + id" @click="randomId">User</router-link> -->
    <router-link to="/news">News</router-link>
  <!-- </div> -->
  <!-- 路由出口：匹配组件最终被渲染位置 -->
  <router-view />
</template>

<script >
import { ref } from '@vue/reactivity';
export default {
  name: 'App',
  setup(props, context) {
    const id = ref(1);

    function randomInRange(min, max) {
      return Math.floor(Math.random() * (max - min)) + min;
    }

    const randomId = () => (id.value = randomInRange(0, 100));

    return {
      id,
      randomId,
    };
  },
};
</script>

<style scoped>
.nav {
  width: 200px;
  display: flex;
  justify-content: space-around;
}
</style>