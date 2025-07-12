<template>
  <Navbar v-if="showNavbar" />
  <router-view />
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import Navbar from '@/components/Navbar.vue'

const route = useRoute()
const showNavbar = computed(() => route.path !== '/')
</script>