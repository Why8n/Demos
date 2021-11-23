<!-- file: App.vue -->
<template>
  <h1>Main Page</h1>

  <div class="nav">
      <button @click="nav2Home">Home</button>
      <button @click="nav2Me">Me</button>
  </div>

  <router-view />
</template>

<script setup>
import { useRouter } from "vue-router"

const router = useRouter();
const nav2Home = () => router.push('/home');
const nav2Me = () => router.push({name:'Me'});

</script>

<style scoped>
.nav button {
    margin-left: 10px;
}
</style>