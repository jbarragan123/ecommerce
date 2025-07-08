<template>
  <div class="header">
    <h1>Productos</h1>
    <button @click="mostrarForm = true">+ Agregar</button>
  </div>

  <ProductList :productos="productos" @productosActualizados="obtenerProductos" />

  <ProductForm v-if="mostrarForm" @productoCreado="onProductoCreado" @cancelar="mostrarForm = false" />
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import api from '@/services/api'
import ProductForm from '@/components/products/ProductForm.vue'
import ProductList from '@/components/products/ProductList.vue'

interface Product {
  id?: number
  name: string
  price: number
  active: boolean
}

const productos = ref<Product[]>([])
const mostrarForm = ref(false)

const obtenerProductos = async () => {
  const response = await api.get('/products')
  productos.value = response.data
}

const onProductoCreado = () => {
  obtenerProductos()
  mostrarForm.value = false
}

onMounted(() => obtenerProductos())
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
button {
  padding: 6px 12px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
}
</style>