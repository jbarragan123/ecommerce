<template>
  <div class="container mt-5">
    <h2 class="mb-4">Inventario</h2>

    <table class="table table-hover">
      <thead>
        <tr>
          <th>ID</th>
          <th>Producto</th>
          <th>Cantidad</th>
          <th>Estado</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in inventory" :key="item.id">
          <td>{{ item.id }}</td>
          <td>{{ item.product.name }}</td>
          <td>{{ item.quantity }}</td>
          <td>
            <span :class="item.active ? 'text-success' : 'text-danger'">
              {{ item.active ? 'Activo' : 'Inactivo' }}
            </span>
          </td>
          <td>
            <button class="btn btn-sm btn-warning me-2" @click="openEdit(item)">Editar</button>
            <button class="btn btn-sm btn-danger" @click="deleteInventory(item.id)" >Inactivar</button>
          </td>
        </tr>
      </tbody>
    </table>

    <h4 class="mt-5">Agregar al Inventario</h4>
    <form @submit.prevent="addInventory">
      <div class="mb-3">
        <label class="form-label">Producto</label>
        <select v-model="selectedProduct" class="form-select" required>
          <option disabled value="">Seleccione un producto</option>
          <option v-for="product in products" :key="product.id" :value="product">
            {{ product.name }}
          </option>
        </select>
      </div>
      <div class="mb-3">
        <label class="form-label">Cantidad</label>
        <input v-model="quantity" type="number" class="form-control" required min="1" />
      </div>
      <button type="submit" class="btn btn-primary">Agregar</button>
    </form>

    <!-- Modal editar -->
    <div v-if="editingInventory" class="modal fade show d-block" tabindex="-1" style="background: rgba(0,0,0,0.5);">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Editar cantidad</h5>
            <button type="button" class="btn-close" @click="closeEdit"></button>
          </div>
          <div class="modal-body">
            <input v-model="editQuantity" type="number" class="form-control" min="0" />
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" @click="closeEdit">Cancelar</button>
            <button class="btn btn-primary" @click="updateInventory">Guardar</button>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>


<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axiosInstance from '@/axiosInstance'

const inventory = ref([])
const products = ref([])

const selectedProduct = ref(null)
const quantity = ref(0)

const editingInventory = ref(null)
const editQuantity = ref(0)

const loadInventory = async () => {
  const { data } = await axiosInstance.get('/inventories')
  inventory.value = data
}

const loadProducts = async () => {
  const { data } = await axiosInstance.get('/products')
  products.value = data
}

const addInventory = async () => {
  await axiosInstance.post('/inventories', {
    product: selectedProduct.value,
    quantity: quantity.value
  })
  await loadInventory()
  selectedProduct.value = null
  quantity.value = 0
}

const deleteInventory = async (id: number) => {
  if (confirm('¿Inactivar este inventario?')) {
    await axiosInstance.put(`/inventories/${id}/deactivate`)
    await loadInventory()
  }
}

const openEdit = (item: any) => {
  editingInventory.value = item
  editQuantity.value = item.quantity
}

const closeEdit = () => {
  editingInventory.value = null
}

const updateInventory = async () => {
  if (editingInventory.value) {
    await axiosInstance.put(`/inventories/${editingInventory.value.id}`, {
      product: editingInventory.value.product,
      quantity: editQuantity.value
    })
    await loadInventory()
    closeEdit()
  }
}


onMounted(() => {
  loadInventory()
  loadProducts()
})
</script>
