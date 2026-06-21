<script setup>
import { inject } from 'vue'

const { archivedMovies, restoreMovie, removeMovie } = inject('movies')
const showToast = inject('showToast')

function onRestore(id) {
  restoreMovie(id)
  showToast?.('Фильм восстановлен в прокат', 'success')
}

function onDelete(id) {
  if (confirm('Удалить фильм из архива?')) {
    removeMovie(id)
    showToast?.('Фильм удалён', 'info')
  }
}
</script>

<template>
  <section>
    <h2 class="section-title">📦 Архив фильмов</h2>

    <div v-if="archivedMovies.length === 0" class="empty-hint">Архив пуст</div>

    <TransitionGroup name="list" tag="ul" class="archive-list">
      <li v-for="movie in archivedMovies" :key="movie.id" class="archive-item">
        <div class="archive-item__info">
          <span class="archive-item__title">{{ movie.title }}</span>
          <span class="archive-item__meta">{{ movie.director }} · {{ movie.genre }}</span>
        </div>
        <div class="archive-item__actions">
          <button class="btn-restore" @click="onRestore(movie.id)">↩️ Восстановить</button>
          <button class="btn-delete" @click="onDelete(movie.id)">🗑 Удалить</button>
        </div>
      </li>
    </TransitionGroup>
  </section>
</template>

<style scoped>
.archive-list {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  padding: 0;
  margin-top: 1rem;
}

.archive-item {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-between;
  padding: 0.8rem 1.2rem;
  background: var(--color-surface);
  border: 1px solid var(--color-border);
  border-radius: 8px;
  border-left: 4px solid #f59e0b;
}

.archive-item__info {
  display: flex;
  flex-direction: column;
}

.archive-item__title {
  font-weight: 600;
}

.archive-item__meta {
  font-size: 0.85rem;
  color: var(--color-text-muted);
}

.archive-item__actions {
  display: flex;
  gap: 0.5rem;
}

.btn-restore {
  padding: 0.3rem 0.8rem;
  border: none;
  border-radius: 6px;
  background: #dcfce7;
  color: #166534;
  cursor: pointer;
}

.btn-restore:hover {
  background: #bbf7d0;
}

.btn-delete {
  padding: 0.3rem 0.8rem;
  border: none;
  border-radius: 6px;
  background: #fee2e2;
  color: #b91c1c;
  cursor: pointer;
}

.btn-delete:hover {
  background: #fecaca;
}

.list-enter-active,
.list-leave-active {
  transition: all 0.3s ease;
}

.list-enter-from,
.list-leave-to {
  opacity: 0;
  transform: translateX(-12px);
}
</style>
