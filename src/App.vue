<!-- file: App.vue -->
<template>
  <h1>Main Page</h1>

  <!-- 点击跳转到根路由 -->
  <router-link to="/home/1">Home 1</router-link>
  <router-link to="/home/2">Home </router-link>
  <router-link to="/me">Me</router-link>

  <router-view />
</template>
<style scoped>
a {
  margin-left: 10px;
}
</style>
