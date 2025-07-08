<template>
  <ul>
    <li v-for="prod in productos" :key="prod.id">
      {{ prod.name }} - ${{ prod.price }}
      <button @click="eliminarProducto(prod.id)">Eliminar</button>
    </li>
  </ul>
</template>

<script setup lang="ts">
import api from '@/services/api'

interface Product {
  id: number
  name: string
  price: number
  active: boolean
}

const props = defineProps<{
  productos: Product[]
}>()

const emit = defineEmits(['productosActualizados'])

const eliminarProducto = async (id: number) => {
  await api.delete(`/products/${id}`)
  emit('productosActualizados')
}
</script>