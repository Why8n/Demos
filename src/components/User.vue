<!-- file: components/User.vue -->
<template>
  <h1>User Component: {{ query }}</h1>
</template>

<script>
import { onBeforeRouteLeave, onBeforeRouteUpdate } from 'vue-router';

export default {
  name: 'User',
  props: {
    query: String,
  },

  setup() {
    onBeforeRouteUpdate((to, from) => {
      console.log('Component onBeforeRouteUupdate');
    });

    onBeforeRouteLeave((to, from) => {
      console.log('Component onBeforeRouteLeave');
    });
  },
};
</script>