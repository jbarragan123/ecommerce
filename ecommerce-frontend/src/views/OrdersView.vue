<script setup lang="ts">
import { ref, onMounted } from 'vue'
import api from '@/services/api'

const orders = ref<any[]>([])

const getOrders = async () => {
  const res = await api.get('/orders')
  orders.value = res.data
}

onMounted(() => {
  getOrders()
})
</script>

<template>
  <h1>📋 Órdenes</h1>
  <ul>
    <li v-for="order in orders" :key="order.id">
      Orden #{{ order.id }} — Total: ${{ order.total }}
    </li>
  </ul>
</template>