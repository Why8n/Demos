<!-- file: components/User.vue -->
<template>
  <h1>User Component: {{ query }}</h1>
</template>

<script>
export default {
  name: 'User',
  props: {
    query: String,
  },
};
</script>