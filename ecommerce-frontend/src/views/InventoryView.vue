<script setup lang="ts">
import { ref, onMounted } from 'vue'
import api from '@/services/api'

const inventories = ref<any[]>([])

const getInventories = async () => {
  const res = await api.get('/inventories')
  inventories.value = res.data
}

onMounted(() => {
  getInventories()
})
</script>

<template>
  <h1>📦 Inventario</h1>
  <ul>
    <li v-for="item in inventories" :key="item.id">
      {{ item.productName }} — Stock: {{ item.stock }}
    </li>
  </ul>
</template>