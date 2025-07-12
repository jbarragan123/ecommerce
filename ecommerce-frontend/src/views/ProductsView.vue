<template>
  <div class="container mt-5">
    <h2 class="mb-4">Productos</h2>

    <!-- Buscador -->
    <div class="mb-3 d-flex gap-2">
      <input v-model="searchQuery" class="form-control w-25" placeholder="Buscar por nombre..." />
      <select v-model="searchActive" class="form-select w-25">
        <option value="">Todos</option>
        <option value="true">Activos</option>
        <option value="false">Inactivos</option>
      </select>
      <button @click="searchProducts" class="btn btn-primary">Buscar</button>
      <button @click="clearSearch" class="btn btn-secondary">Limpiar</button>
    </div>

    <!-- Formulario crear producto -->
    <div class="card mb-4">
      <div class="card-body">
        <h5 class="card-title">Nuevo producto</h5>
        <form @submit.prevent="createProduct">
          <div class="row g-3">
            <div class="col-md-3">
              <input v-model="newProduct.name" class="form-control" placeholder="Nombre" required />
            </div>
            <div class="col-md-3">
              <input v-model="newProduct.description" class="form-control" placeholder="Descripción" />
            </div>
            <div class="col-md-2">
              <input v-model="newProduct.price" type="number" step="0.01" min="0" class="form-control" placeholder="Precio" required />
            </div>
            <div class="col-md-2 form-check mt-2">
              <input v-model="newProduct.active" type="checkbox" class="form-check-input" />
              <label class="form-check-label ms-2">Activo</label>
            </div>
            <div class="col-md-2">
              <button type="submit" class="btn btn-primary w-100">Crear</button>
            </div>
          </div>
        </form>
      </div>
    </div>

    <!-- Tabla de productos -->
    <table class="table table-striped">
      <thead>
        <tr>
          <th>ID</th>
          <th>Nombre</th>
          <th>Descripción</th>
          <th>Precio</th>
          <th>Activo</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in products" :key="product.id">
          <td>{{ product.id }}</td>
          <td>{{ product.name }}</td>
          <td>{{ product.description }}</td>
          <td>$ {{ product.price }}</td>
          <td>
            <span :class="product.active ? 'badge bg-success' : 'badge bg-secondary'">
              {{ product.active ? 'Sí' : 'No' }}
            </span>
          </td>
          <td>
            <button class="btn btn-sm btn-warning" @click="openEditModal(product)">Editar</button>
            <button class="btn btn-sm btn-danger ms-2" @click="deleteProduct(product.id)">Eliminar</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Modal de edición -->
    <div v-if="showEditModal" class="modal fade show d-block" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <form @submit.prevent="updateProduct">
            <div class="modal-header">
              <h5 class="modal-title">Editar Producto</h5>
              <button type="button" class="btn-close" @click="closeEditModal"></button>
            </div>
            <div class="modal-body">
              <div class="mb-3">
                <label class="form-label">Nombre</label>
                <input v-model="editProduct.name" class="form-control" required />
              </div>
              <div class="mb-3">
                <label class="form-label">Descripción</label>
                <input v-model="editProduct.description" class="form-control" />
              </div>
              <div class="mb-3">
                <label class="form-label">Precio</label>
                <input v-model="editProduct.price" type="number" step="0.01" min="0" class="form-control" required />
              </div>
              <div class="form-check mb-3">
                <input v-model="editProduct.active" type="checkbox" class="form-check-input" />
                <label class="form-check-label">Activo</label>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" @click="closeEditModal">Cancelar</button>
              <button type="submit" class="btn btn-primary">Guardar cambios</button>
            </div>
          </form>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axiosInstance from '@/axiosInstance'

const products = ref([])
const showEditModal = ref(false)
const searchQuery = ref('')
const searchActive = ref('')

const newProduct = ref({
  name: '',
  description: '',
  price: 0,
  active: true
})

const editProduct = ref({
  id: 0,
  name: '',
  description: '',
  price: 0,
  active: true
})

const loadProducts = async () => {
  const response = await axiosInstance.get('/products')
  products.value = response.data
}


const searchProducts = async () => {
  const params: any = {}
  if (searchQuery.value) params.query = searchQuery.value
  if (searchActive.value !== '') params.active = searchActive.value

  const response = await axiosInstance.get('/products/search', { params })
  products.value = response.data
}

const clearSearch = () => {
  searchQuery.value = ''
  searchActive.value = ''
  loadProducts()
}

const createProduct = async () => {
  await axiosInstance.post('/products', newProduct.value)
  await loadProducts()
  newProduct.value = { name: '', description: '', price: 0, active: true }
}

const openEditModal = (product: any) => {
  editProduct.value = { ...product }
  showEditModal.value = true
}

const closeEditModal = () => {
  showEditModal.value = false
}

const updateProduct = async () => {
  await axiosInstance.put(`/products/${editProduct.value.id}`, editProduct.value)
  await loadProducts()
  closeEditModal()
}

const deleteProduct = async (id: number) => {
  if (confirm('¿Estás seguro de eliminar este producto?')) {
    await axiosInstance.delete(`/products/${id}`)
    await loadProducts()
  }
}

onMounted(loadProducts)
</script>
