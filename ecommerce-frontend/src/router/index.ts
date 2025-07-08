import { createRouter, createWebHistory } from 'vue-router'
import ProductsView from '@/views/ProductsView.vue'

const routes = [
  { path: '/products', name: 'Products', component: ProductsView }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router