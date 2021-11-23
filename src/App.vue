<!-- file: App.vue -->
<template>
  <h1>Main Page</h1>
  
  <!-- 点击跳转到根路由 -->
  <router-link to="/">Main</router-link>
  
  <router-view name="sidebar" />
  <router-view />
  
</template>
