<script setup lang="ts">
import { ref, onMounted } from 'vue'
import api from '@/services/api'

const users = ref<any[]>([])

const getUsers = async () => {
  const res = await api.get('/users')
  users.value = res.data
}

onMounted(() => {
  getUsers()
})
</script>

<template>
  <h1>👤 Usuarios</h1>
  <ul>
    <li v-for="user in users" :key="user.id">
      {{ user.username }} — {{ user.email }}
    </li>
  </ul>
</template>