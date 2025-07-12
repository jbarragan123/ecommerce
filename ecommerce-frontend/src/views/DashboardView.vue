<template>
  <div class="container mt-5">
    <h1 class="mb-4">Dashboard</h1>
    <div class="btn-group mb-4">
      <router-link to="/products" class="btn btn-outline-primary">Productos</router-link>
      <router-link to="/inventory" class="btn btn-outline-primary">Inventario</router-link>
      <router-link to="/orders" class="btn btn-outline-primary">Órdenes</router-link>
      <router-link to="/users" class="btn btn-outline-primary">Usuarios</router-link>
      <router-link to="/reports" class="btn btn-outline-primary">Reportes</router-link>
    </div>
    <!-- <div>
      <button @click="logout" class="btn btn-danger">Cerrar sesión</button>
    </div> -->
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
const router = useRouter()

const logout = () => {
  localStorage.removeItem('token')
  router.push('/')
}
</script>
