<template>
  <form @submit.prevent="crearProducto">
    <input v-model="producto.name" placeholder="Nombre" required />
    <input v-model.number="producto.price" type="number" placeholder="Precio" required />
    <button type="submit">Guardar</button>
    <button type="button" @click="$emit('cancelar')">Cancelar</button>
  </form>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import api from '@/services/api'

const emit = defineEmits(['productoCreado'])

interface Product {
  name: string
  price: number
  active: boolean
}

const producto = ref<Product>({ name: '', price: 0, active: true })

const crearProducto = async () => {
  await api.post('/products', producto.value)
  producto.value = { name: '', price: 0, active: true }
  emit('productoCreado')
}
</script>