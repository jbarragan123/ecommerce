<template>
  <div class="container mt-5" style="max-width: 400px;">
    <h2 class="mb-4 text-center">Registrarse</h2>
    <form @submit.prevent="register">
      <div class="mb-3">
        <label class="form-label">Nombre de usuario</label>
        <input v-model="username" type="text" class="form-control" required />
      </div>
      <div class="mb-3">
        <label class="form-label">Correo electrónico</label>
        <input v-model="email" type="email" class="form-control" required />
      </div>
      <div class="mb-3">
        <label class="form-label">Contraseña</label>
        <input v-model="password" type="password" class="form-control" required />
      </div>
      <button type="submit" class="btn btn-success w-100">Registrarse</button>
    </form>
    <div class="text-center mt-3">
      <router-link to="/">¿Ya tienes cuenta? Inicia sesión</router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axiosInstance from '@/axiosInstance'

const username = ref('')
const email = ref('')
const password = ref('')
const router = useRouter()

const register = async () => {
  try {
    await axiosInstance.post('/users/register', {
      username: username.value,
      email: email.value,
      password: password.value
    })
    alert('Registro exitoso. Ahora puedes iniciar sesión.')
    router.push('/')
  } catch (error) {
    alert('Error en el registro')
  }
}
</script>
