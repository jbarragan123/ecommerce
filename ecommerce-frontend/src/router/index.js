import { createRouter, createWebHistory } from 'vue-router';
import ProductsView from '@/views/ProductsView.vue';
import InventoryView from '@/views/InventoryView.vue';
import OrdersView from '@/views/OrdersView.vue';
import UsersView from '@/views/UsersView.vue';
import LoginView from '@/views/LoginView.vue';
import DashboardView from '@/views/DashboardView.vue';
import ReportsView from '@/views/ReportsView.vue';
const routes = [
    { path: '/', name: 'Login', component: LoginView },
    { path: '/dashboard', name: 'Dashboard', component: DashboardView },
    { path: '/products', name: 'Products', component: ProductsView },
    { path: '/inventory', name: 'Inventory', component: InventoryView },
    { path: '/orders', name: 'Orders', component: OrdersView },
    { path: '/users', name: 'Users', component: UsersView },
    { path: '/reports', name: 'Reports', component: ReportsView }
];
const router = createRouter({
    history: createWebHistory(),
    routes
});
export default router;
