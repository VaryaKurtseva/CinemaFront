<script setup>
import { inject, computed } from 'vue'
import BaseButton from '../ui/BaseButton.vue'

defineProps({
  activeTab: { type: String, required: true },
})

const emit = defineEmits(['change-tab'])

const moviesStore = inject('movies')

const tabs = [
  { id: 'movies', label: '🎬 Афиша', badge: true },
  { id: 'archive', label: '📦 Архив' },
]

const activeCount = computed(() => moviesStore?.activeCount?.value ?? 0)
</script>

<template>
  <nav class="nav">
    <BaseButton
      v-for="tab in tabs"
      :key="tab.id"
      :variant="activeTab === tab.id ? 'primary' : 'ghost'"
      @click="emit('change-tab', tab.id)"
    >
      {{ tab.label }}
      <span v-if="tab.badge && activeCount > 0" class="nav__badge">{{ activeCount }}</span>
    </BaseButton>
  </nav>
</template>

<style scoped>
.nav {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-top: 1rem;
}
.nav__badge {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  min-width: 1.25rem;
  height: 1.25rem;
  margin-left: 0.35rem;
  padding: 0 0.35rem;
  font-size: 0.7rem;
  font-weight: 700;
  background: #ef4444;
  color: white;
  border-radius: 999px;
}
</style>
