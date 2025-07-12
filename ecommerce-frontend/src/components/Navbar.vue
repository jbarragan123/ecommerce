<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
      <router-link class="navbar-brand" to="/dashboard">E-Commerce</router-link>

      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link class="nav-link" to="/products">Productos</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/inventory">Inventario</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/orders">Órdenes</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/users">Usuarios</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/reports">Reportes</router-link>
          </li>
        </ul>

        <button class="btn btn-outline-light" @click="logout">Cerrar Sesión</button>
      </div>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

const logout = () => {
  localStorage.removeItem('token')
  router.push('/')
}
</script>
