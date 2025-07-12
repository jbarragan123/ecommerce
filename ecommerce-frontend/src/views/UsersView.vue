<template>
  <div class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
      <h2>Usuarios</h2>
      <button v-if="!showAddForm" class="btn btn-outline-primary" @click="showAddForm = true">
        + Nuevo Usuario
      </button>
      <button v-else class="btn btn-outline-secondary" @click="cancelAdd">
        Cancelar creación
      </button>
    </div>

    <table class="table table-hover">
      <thead>
        <tr>
          <th>ID</th>
          <th>Usuario</th>
          <th>Email</th>
          <th>Frecuente</th>
          <th>Activo</th>
          <th>Acciones</th>
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
          <td>
            <span :class="user.active ? 'badge bg-success' : 'badge bg-secondary'">
              {{ user.active ? 'Activo' : 'Inactivo' }}
            </span>
          </td>
          <td>
            <button class="btn btn-sm btn-warning me-1" @click="editUser(user)">Editar</button>
            <button 
              v-if="user.active" 
              class="btn btn-sm btn-danger" 
              @click="inactivateUser(user.id)">
              Inactivar
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Formulario de edición -->
    <div v-if="selectedUser" class="mt-4">
      <h4>Editar Usuario: {{ selectedUser.username }}</h4>
      <form @submit.prevent="updateUser">
        <div class="mb-3">
          <label class="form-label">Email</label>
          <input v-model="selectedUser.email" type="email" class="form-control" required />
        </div>
        <div class="form-check mb-3">
          <input style="border-color: black;" v-model="selectedUser.frequentClient" type="checkbox" class="form-check-input" id="frequentEdit" />
          <label for="frequentEdit" class="form-check-label">Cliente frecuente</label>
        </div>
        <button type="submit" class="btn btn-success">Guardar Cambios</button>
        <button type="button" class="btn btn-secondary ms-2" @click="cancelEdit">Cancelar</button>
      </form>
    </div>

    <!-- Formulario de agregar -->
    <div v-if="showAddForm" class="mt-5">
      <h4>Agregar usuario</h4>
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
          <input v-model="frequentClient" type="checkbox" class="form-check-input" id="frequentCheck" />
          <label for="frequentCheck" class="form-check-label">Cliente frecuente</label>
        </div>
        <button class="btn btn-success">Registrar</button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axiosInstance from '@/axiosInstance'

const users = ref([])
const username = ref('')
const email = ref('')
const password = ref('')
const frequentClient = ref(false)
const selectedUser = ref(null)
const showAddForm = ref(false)

const loadUsers = async () => {
  const response = await axiosInstance.get('/users')
  users.value = response.data
}

const addUser = async () => {
  await axiosInstance.post('/users/register', {
    username: username.value,
    password: password.value,
    email: email.value,
    frequentClient: frequentClient.value
  })
  await loadUsers()
  username.value = ''
  email.value = ''
  password.value = ''
  frequentClient.value = false
  showAddForm.value = false
}

const editUser = (user) => {
  selectedUser.value = { ...user }
}

const cancelEdit = () => {
  selectedUser.value = null
}

const cancelAdd = () => {
  showAddForm.value = false
  username.value = ''
  email.value = ''
  password.value = ''
  frequentClient.value = false
}

const updateUser = async () => {
  await axiosInstance.put(`/users/${selectedUser.value.id}`, {
    email: selectedUser.value.email,
    frequentClient: selectedUser.value.frequentClient
  })
  await loadUsers()
  selectedUser.value = null
}

const inactivateUser = async (id) => {
  if (confirm('¿Seguro que deseas inactivar este usuario?')) {
    await axiosInstance.patch(`/users/${id}/inactivate`)
    await loadUsers()
  }
}

onMounted(loadUsers)
</script> 