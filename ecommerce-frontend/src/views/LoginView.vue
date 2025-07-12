<template>
  <div class="container mt-5" style="max-width: 400px;">
    <h2 class="mb-4 text-center">Iniciar sesión</h2>
    <form @submit.prevent="login">
      <div class="mb-3">
        <label class="form-label">Usuario</label>
        <input v-model="username" type="text" class="form-control" required />
      </div>
      <div class="mb-3">
        <label class="form-label">Contraseña</label>
        <input v-model="password" type="password" class="form-control" required />
      </div>
      <button type="submit" class="btn btn-primary w-100">Ingresar</button>
      <div class="text-center mt-3">
        <router-link to="/register">¿No tienes cuenta? Regístrate</router-link>
      </div>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axiosInstance from '@/axiosInstance'

const username = ref('')
const password = ref('')
const router = useRouter()

const login = async () => {
  try {
    const response = await axiosInstance.post('/auth/login', {
      username: username.value,
      password: password.value
    })
    localStorage.setItem('token', response.data)
    router.push('/dashboard')
  } catch (error) {
    alert('Credenciales incorrectas')
  }
}
</script>
