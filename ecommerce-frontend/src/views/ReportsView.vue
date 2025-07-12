<template>
  <div class="container mt-5">
    <h2 class="mb-4">📊 Reportes</h2>

    <h4 class="mt-4">Top 5 Productos Más Vendidos</h4>
    <table class="table table-sm table-striped">
      <thead>
        <tr>
          <th>Producto</th>
          <th>Cantidad Vendida</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(prod, idx) in topProducts" :key="idx">
          <td>{{ prod[0] }}</td>
          <td>{{ prod[1] }}</td>
        </tr>
      </tbody>
    </table>

    <h4 class="mt-5">Top 5 Clientes Frecuentes</h4>
    <table class="table table-sm table-striped">
      <thead>
        <tr>
          <th>Cliente</th>
          <th>Número de Órdenes</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(cli, idx) in topClients" :key="idx">
          <td>{{ cli[0] }}</td>
          <td>{{ cli[1] }}</td>
        </tr>
      </tbody>
    </table>

    <h4 class="mt-5">Productos Activos</h4>
    <table class="table table-sm table-striped">
      <thead>
        <tr>
          <th>ID</th>
          <th>Nombre</th>
          <th>Precio</th>
          <th>Activo</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in activeProducts" :key="product.id">
          <td>{{ product.id }}</td>
          <td>{{ product.name }}</td>
          <td>${{ product.price.toFixed(2) }}</td>
          <td>{{ product.active ? 'Sí' : 'No' }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'

const token = localStorage.getItem('token')

const topProducts = ref([])
const topClients = ref([])
const activeProducts = ref([])

const loadReports = async () => {
  const headers = { Authorization: `Bearer ${token}` }

  const productsRes = await axios.get('http://localhost:8080/api/orders/reports/top-products', { headers })
  topProducts.value = productsRes.data

  const clientsRes = await axios.get('http://localhost:8080/api/orders/reports/top-clients', { headers })
  topClients.value = clientsRes.data

  const activeRes = await axios.get('http://localhost:8080/api/products/active', { headers })
  activeProducts.value = activeRes.data
}

onMounted(() => {
  loadReports()
})
</script>
