<template>
  <div class="container mt-5">
    <h2 class="mb-4">Usuarios</h2>

    <table class="table table-hover">
      <thead>
        <tr>
          <th>ID</th>
          <th>Usuario</th>
          <th>Email</th>
          <th>Frecuente</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id">
          <td>{{ user.id }}</td>
          <td>{{ user.username }}</td>
          <td>{{ user.email }}</td>
          <td>
            <span :class="user.frequentClient ? 'badge bg-info' : 'badge bg-light text-dark'">
              {{ user.frequentClient ? 'Sí' : 'No' }}
            </span>
          </td>
        </tr>
      </tbody>
    </table>

    <h4 class="mt-5">Agregar usuario</h4>
    <form @submit.prevent="addUser">
      <div class="mb-3">
        <label class="form-label">Usuario</label>
        <input v-model="username" class="form-control" required />
      </div>
      <div class="mb-3">
        <label class="form-label">Email</label>
        <input v-model="email" type="email" class="form-control" required />
      </div>
      <div class="mb-3">
        <label class="form-label">Contraseña</label>
        <input v-model="password" type="password" class="form-control" required />
      </div>
      <div class="form-check mb-3">
        <input v-model="frequentClient" type="checkbox" class="form-check-input" id="frequentCheck" style="border-color: black;"/>
        <label for="frequentCheck" class="form-check-label">Cliente frecuente</label>
      </div>
      <button class="btn btn-success">Registrar</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'

const users = ref([])
const username = ref('')
const email = ref('')
const password = ref('')
const frequentClient = ref(false)

const token = localStorage.getItem('token')

const loadUsers = async () => {
  const response = await axios.get('http://localhost:8080/api/users', {
    headers: { Authorization: `Bearer ${token}` }
  })
  users.value = response.data
}

const addUser = async () => {
  await axios.post('http://localhost:8080/api/users/register', {
    username: username.value,
    password: password.value,
    email: email.value,
    frequentClient: frequentClient.value
  }, {
    headers: { Authorization: `Bearer ${token}` }
  })
  await loadUsers()
  username.value = ''
  email.value = ''
  password.value = ''
  frequentClient.value = false
}

onMounted(loadUsers)
</script>
