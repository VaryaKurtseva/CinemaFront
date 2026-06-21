<script setup>
import { ref, computed, inject } from 'vue'
import MovieCard from './MovieCard.vue'
import BaseCard from '../ui/BaseCard.vue'
import BaseInput from '../ui/BaseInput.vue'
import BaseButton from '../ui/BaseButton.vue'

const { movies, addMovie, removeMovie, archiveMovie } = inject('movies')
const showToast = inject('showToast')

const filter = ref('all')

const newMovie = ref({
  title: '',
  director: '',
  genre: 'Фантастика',
  duration: 120,
  releaseDate: '',
})

const genres = [
  'Фантастика',
  'Драма',
  'Комедия',
  'Боевик',
  'Триллер',
  'Мелодрама',
  'Ужасы',
  'Приключения',
]

const filteredMovies = computed(() => {
  let list = [...movies.value]

  if (filter.value === 'active') {
    list = list.filter((m) => m.status === 'active')
  } else if (filter.value === 'archived') {
    list = list.filter((m) => m.status === 'archived')
  }

  return list
})

function submitMovie() {
  if (!newMovie.value.title.trim()) {
    showToast?.('Введите название фильма', 'warning')
    return
  }
  if (!newMovie.value.director.trim()) {
    showToast?.('Введите имя режиссёра', 'warning')
    return
  }

  addMovie(newMovie.value)
  showToast?.('Фильм добавлен в афишу', 'success')

  newMovie.value = { title: '', director: '', genre: 'Фантастика', duration: 120, releaseDate: '' }
}

function onDelete(id) {
  if (confirm('Удалить фильм?')) {
    removeMovie(id)
    showToast?.('Фильм удалён', 'info')
  }
}

function onArchive(id) {
  archiveMovie(id)
  showToast?.('Фильм перемещён в архив', 'info')
}
</script>

<template>
  <section>
    <h2 class="section-title">🎬 Афиша кинотеатра</h2>

    <BaseCard title="Добавить фильм">
      <form class="movie-form" @submit.prevent="submitMovie">
        <BaseInput v-model="newMovie.title" label="Название" placeholder="Введите название" />
        <BaseInput v-model="newMovie.director" label="Режиссёр" placeholder="Имя режиссёра" />

        <div class="form-row">
          <label class="select-wrap">
            <span class="select-wrap__label">Жанр</span>
            <select v-model="newMovie.genre" class="select-wrap__field">
              <option v-for="g in genres" :key="g" :value="g">{{ g }}</option>
            </select>
          </label>
          <BaseInput v-model.number="newMovie.duration" label="Длительность (мин)" type="number" />
          <BaseInput v-model="newMovie.releaseDate" label="Дата премьеры" type="date" />
        </div>

        <BaseButton type="submit" variant="primary">Добавить фильм</BaseButton>
      </form>
    </BaseCard>

    <div class="filter-tabs">
      <BaseButton
        v-for="tab in [
          { id: 'all', label: 'Все' },
          { id: 'active', label: 'В прокате' },
          { id: 'archived', label: 'Архив' },
        ]"
        :key="tab.id"
        :variant="filter === tab.id ? 'primary' : 'ghost'"
        @click="filter = tab.id"
      >
        {{ tab.label }}
      </BaseButton>
    </div>

    <p class="results-hint">Найдено: {{ filteredMovies.length }}</p>

    <TransitionGroup name="list" tag="ul" class="movie-list">
      <MovieCard
        v-for="movie in filteredMovies"
        :key="movie.id"
        :movie="movie"
        @archive="onArchive"
        @delete="onDelete"
      />
    </TransitionGroup>

    <p v-if="!filteredMovies.length" class="empty-hint">Фильмов не найдено</p>
  </section>
</template>

<style scoped>
.movie-form {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: 0.75rem;
}

.select-wrap {
  display: flex;
  flex-direction: column;
  gap: 0.35rem;
}

.select-wrap__label {
  font-size: 0.85rem;
  font-weight: 500;
  color: var(--color-text-muted);
}

.select-wrap__field {
  padding: 0.55rem 0.75rem;
  border: 1px solid var(--color-border);
  border-radius: 8px;
  background: var(--color-surface);
  color: var(--color-text);
}

.filter-tabs {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin: 1rem 0;
}

.results-hint {
  font-size: 0.85rem;
  color: var(--color-text-muted);
  margin-bottom: 0.5rem;
}

.movie-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 1rem;
  padding: 0;
  margin: 0;
}

.list-enter-active,
.list-leave-active {
  transition: all 0.3s ease;
}

.list-enter-from,
.list-leave-to {
  opacity: 0;
  transform: scale(0.95);
}

@media (max-width: 768px) {
  .form-row {
    grid-template-columns: 1fr;
  }
}
</style>
