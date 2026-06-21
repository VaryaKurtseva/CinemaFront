<script setup>
defineProps({
  movie: { type: Object, required: true },
})

const emit = defineEmits(['archive', 'delete'])

function formatDate(dateStr) {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleDateString('ru-RU', { day: 'numeric', month: 'long', year: 'numeric' })
}

const ratingStars = (rating) => {
  if (rating == null) return '☆☆☆☆☆'
  const full = Math.floor(rating)
  const empty = 5 - full
  return '★'.repeat(full) + '☆'.repeat(empty)
}
</script>

<template>
  <li class="movie-card">
    <div class="movie-card__head">
      <h3 class="movie-card__title">{{ movie.title }}</h3>
      <span
        class="movie-card__status"
        :class="movie.status === 'active' ? 'status-active' : 'status-archived'"
      >
        {{ movie.status === 'active' ? '🎬 В прокате' : '📦 Архив' }}
      </span>
    </div>

    <p class="movie-card__director">Режиссёр: {{ movie.director }}</p>

    <div class="movie-card__meta">
      <span class="movie-card__genre">{{ movie.genre }}</span>
      <span class="movie-card__duration">⏱ {{ movie.duration }} мин</span>
      <span class="movie-card__date">📅 {{ formatDate(movie.releaseDate) }}</span>
    </div>

    <div class="movie-card__rating">
      <span class="movie-card__stars">{{ ratingStars(movie.rating) }}</span>
      <span class="movie-card__rating-value">{{
        movie.rating != null ? movie.rating.toFixed(1) : '0.0'
      }}</span>
    </div>

    <div class="movie-card__actions">
      <button
        v-if="movie.status === 'active'"
        class="btn-action btn-archive"
        @click="emit('archive', movie.id)"
      >
        📦 Архив
      </button>
      <button class="btn-action btn-delete" @click="emit('delete', movie.id)">🗑 Удалить</button>
    </div>
  </li>
</template>

<style scoped>
.movie-card {
  list-style: none;
  padding: 1.2rem;
  background: var(--color-surface);
  border-radius: 12px;
  border: 1px solid var(--color-border);
  transition: all 0.2s;
}

.movie-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.movie-card__head {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  flex-wrap: wrap;
}

.movie-card__title {
  font-size: 1.1rem;
  font-weight: 600;
  flex: 1;
}

.movie-card__status {
  font-size: 0.7rem;
  padding: 0.2rem 0.6rem;
  border-radius: 20px;
  font-weight: 600;
}

.status-active {
  background: #dcfce7;
  color: #166534;
}

.status-archived {
  background: #fef3c7;
  color: #92400e;
}

.movie-card__director {
  font-size: 0.9rem;
  color: var(--color-text-muted);
}

.movie-card__meta {
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
  margin-top: 0.3rem;
}

.movie-card__genre {
  background: var(--color-bg);
  padding: 0.2rem 0.6rem;
  border-radius: 12px;
  font-size: 0.8rem;
}

.movie-card__duration,
.movie-card__date {
  font-size: 0.8rem;
  color: var(--color-text-muted);
}

.movie-card__rating {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-top: 0.3rem;
}

.movie-card__stars {
  font-size: 1.1rem;
  letter-spacing: 0.1rem;
}

.movie-card__rating-value {
  font-weight: 700;
  color: var(--color-primary);
}

.movie-card__actions {
  display: flex;
  gap: 0.5rem;
  margin-top: 0.5rem;
}

.btn-action {
  padding: 0.3rem 1rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.8rem;
  transition: 0.2s;
}

.btn-archive {
  background: #fef3c7;
  color: #92400e;
}

.btn-archive:hover {
  background: #fde68a;
}

.btn-delete {
  background: #fee2e2;
  color: #b91c1c;
}

.btn-delete:hover {
  background: #fecaca;
}
</style>
