<template>
  <div class="container mt-5">
    <h2 class="mb-4">Auditoría</h2>

    <table class="table table-bordered">
      <thead>
        <tr>
          <th>ID</th>
          <th>Acción</th>
          <th>Entidad</th>
          <th>Fecha</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="item in auditoria" :key="item.id">
            <td>{{ item.id }}</td>
            <td>{{ item.action }}</td>
            <td>{{ item.entity }}</td>
            <td>{{ item.timestamp }}</td>
        </tr>
      </tbody>
    </table>

  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axiosInstance from '@/axiosInstance' 

const auditoria = ref([])

const loadAuditoria = async () => {
  const { data } = await axiosInstance.get('/audit')
  auditoria.value = data
}
onMounted(() => {
  loadAuditoria()
})

</script>
