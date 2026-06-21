<script setup>
/**
 * Шапка сайта — статичный блок с названием приложения.
 * Не содержит логики, только разметка (presentational component).
 */

import { inject } from 'vue'

const { isDark, toggleTheme } = inject('theme')
</script>

<template>
  <header class="header">
    <div class="header__brand">
      <span class="header__icon" aria-hidden="true">🎬</span>
      <div>
        <h1 class="header__title">КиноМир</h1>
        <p class="header__subtitle">Управление кинотеатром на Vue.js</p>
      </div>
    </div>
    <button class="theme-toggle" @click="toggleTheme">
      {{ isDark ? '☀️' : '🌙' }}
    </button>
  </header>
</template>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 1rem;
  border-bottom: 1px solid var(--color-border);
}

.header__brand {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.header__icon {
  font-size: 2.5rem;
}

.header__title {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--color-text);
}

.header__subtitle {
  font-size: 0.875rem;
  color: var(--color-text-muted);
}

.theme-toggle {
  padding: 0.4rem 0.8rem;
  border: 1px solid var(--color-border);
  border-radius: 8px;
  background: var(--color-surface);
  cursor: pointer;
  font-size: 1.2rem;
  transition: 0.2s;
}

.theme-toggle:hover {
  background: var(--color-bg);
}
</style>
