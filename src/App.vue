<template>
  <h1>Main Page</h1>
  <div class="nav">
    <!-- router-link 最终会被渲染为一个 a 标签 -->
    <router-link to="/home">Home</router-link>
    <router-link to="/me">Me</router-link>
  </div>
  <!-- 路由出口：匹配组件最终被渲染位置 -->
  <router-view />
</template>

<style scoped>
.nav {
  width: 100px;
  display: flex;
  justify-content: space-around;
}
</style>