<template>
  <div class="container mt-5">
    <h2 class="mb-4">Órdenes</h2>

    <button class="btn btn-primary mb-3" @click="showForm = true">Nueva Orden</button>

    <table class="table table-bordered">
      <thead>
        <tr>
          <th>ID</th>
          <th>Cliente</th>
          <th>Fecha</th>
          <th>Total</th>
          <th>Descuento</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="order in orders" :key="order.id">
          <td>{{ order.id }}</td>
          <td>{{ order.user.username }}</td>
          <td>{{ formatDate(order.orderDate) }}</td>
          <td>$ {{ order.totalAmount.toFixed(2) }}</td>
          <td>$ {{ order.discountApplied.toFixed(2) }}</td>
          <td>
            <button class="btn btn-sm btn-info me-2" @click="viewOrder(order.id)">Ver</button>
            <button
              class="btn btn-sm btn-danger"
              @click="handleDeleteOrder(order.id)"
            >
              Inactivar
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Formulario Nueva Orden -->
    <div v-if="showForm" class="card p-4 mt-4">
      <h4>Nueva Orden</h4>
      <form @submit.prevent="createOrder">
        <div class="mb-3">
          <label>Cliente</label>
          <select v-model="selectedUser" class="form-select" required>
            <option disabled value="">Seleccione un usuario</option>
            <option v-for="user in users" :key="user.id" :value="user.id">
              {{ user.username }}
            </option>
          </select>
        </div>

        <div v-for="(item, index) in orderItems" :key="index" class="mb-3">
          <label>Producto</label>
          <select v-model="item.productId" class="form-select" required>
            <option disabled value="">Seleccione un producto</option>
            <option v-for="product in products" :key="product.id" :value="product.id">
              {{ product.name }}
            </option>
          </select>

          <label class="mt-2">Cantidad</label>
          <input v-model="item.quantity" type="number" class="form-control" min="1" required />

          <button class="btn btn-sm btn-danger mt-2" type="button" @click="removeItem(index)">Quitar</button>
        </div>

        <button type="button" class="btn btn-secondary mb-3" @click="addItem">+ Producto</button>

        <div class="form-check mb-3">
          <input
            class="form-check-input"
            type="checkbox"
            v-model="isRandom"
            id="randomCheck"
            style="border-color:black"
          />
          <label class="form-check-label" for="randomCheck">Pedido Aleatorio (50% desc)</label>
        </div>

        <button type="submit" class="btn btn-primary">Guardar Orden</button>
        <button type="button" class="btn btn-secondary ms-2" @click="showForm = false">Cancelar</button>
      </form>
    </div>

    <!-- Detalle de Orden -->
    <div v-if="selectedOrder" class="card p-4 mt-4">
      <h4>Detalle de Orden #{{ selectedOrder.id }}</h4>
      <p><strong>Cliente:</strong> {{ selectedOrder.user.username }}</p>
      <p><strong>Fecha:</strong> {{ formatDate(selectedOrder.orderDate) }}</p>
      <p><strong>Total:</strong> ${{ selectedOrder.totalAmount.toFixed(2) }}</p>
      <p><strong>Descuento aplicado:</strong> ${{ selectedOrder.discountApplied.toFixed(2) }}</p>

      <h5 class="mt-3">Items:</h5>
      <table class="table table-sm">
        <thead>
          <tr>
            <th>Producto</th>
            <th>Cantidad</th>
            <th>Precio Unitario</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="item in selectedOrderItems" :key="item.id">
            <td>{{ item.product.name }}</td>
            <td>{{ item.quantity }}</td>
            <td>$ {{ item.price.toFixed(2) }}</td>
          </tr>
        </tbody>
      </table>

      <button class="btn btn-secondary mt-3" @click="selectedOrder = null">Cerrar Detalle</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axiosInstance from '@/axiosInstance' // usa tu instancia personalizada

const orders = ref([])
const users = ref([])
const products = ref([])

const showForm = ref(false)
const selectedUser = ref('')
const isRandom = ref(false)

const orderItems = ref([{ productId: '', quantity: 1 }])

const selectedOrder = ref(null)
const selectedOrderItems = ref([])

const loadOrders = async () => {
  const res = await axiosInstance.get('/orders')
  orders.value = res.data
}

const loadUsers = async () => {
  const res = await axiosInstance.get('/users')
  users.value = res.data
}

const loadProducts = async () => {
  const res = await axiosInstance.get('/products/active')
  products.value = res.data
}

const createOrder = async () => {
  const payload = {
    userId: selectedUser.value,
    orderDate: new Date().toISOString(),
    random: isRandom.value,
    items: orderItems.value.map(i => ({
      product: { id: i.productId },
      quantity: i.quantity
    }))
  }

  await axiosInstance.post('/orders/with-items', payload)

  await loadOrders()

  showForm.value = false
  selectedUser.value = ''
  orderItems.value = [{ productId: '', quantity: 1 }]
  isRandom.value = false
}

const viewOrder = async (id: number) => {
  const orderRes = await axiosInstance.get(`/orders/${id}`)
  selectedOrder.value = orderRes.data

  const itemsRes = await axiosInstance.get(`/orders/order-items?orderId=${id}`)
  selectedOrderItems.value = itemsRes.data
}

const deleteOrder = async (id: number) => {
  await axiosInstance.delete(`/orders/${id}`)
  await loadOrders()
}


const addItem = () => {
  orderItems.value.push({ productId: '', quantity: 1 })
}

const removeItem = (index: number) => {
  orderItems.value.splice(index, 1)
}

const formatDate = (iso: string) => {
  return new Date(iso).toLocaleString()
}

const handleDeleteOrder = async (id: number) => {
  if (confirm('¿Seguro que deseas desactivar esta orden?')) {
    try {
      await deleteOrder(id)
    } catch (error) {
      console.error('Error al eliminar la orden:', error)
      alert('Ocurrió un error al eliminar la orden.')
    }
  }
}

onMounted(() => {
  loadOrders()
  loadUsers()
  loadProducts()
})
</script>
