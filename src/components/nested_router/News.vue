<!-- file: components/nested_router/News.vue -->
<template>
  <h1>News Component</h1>

  <div class="nav">
    <router-link to="/news/finance">Finance</router-link>
    <router-link to="/news/sports">Sports</router-link>
  </div>
  <router-view />
</template>

<style scoped>
/* router-link 最终会被转换为 a 标签 */
.nav a {
  margin-left: 10px;
}
</style>
