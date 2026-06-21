<script setup>
import { ref, provide, onMounted, computed } from 'vue'
import AppHeader from './components/layout/AppHeader.vue'
import AppNavigation from './components/layout/AppNavigation.vue'
import AppToast from './components/ui/AppToast.vue'
import MovieList from './components/movies/MovieList.vue'
import ArchiveList from './components/movies/ArchiveList.vue'
import { useTheme } from './composables/useTheme.js'
import { useMoviesAPI } from './composables/useMoviesAPI.js'
import { useToast } from './composables/useToast.js'

const activeTab = ref('movies')

const theme = useTheme()
const { toasts, showToast, removeToast } = useToast()

const { movies, loading, error, fetchMovies, createMovie, updateMovieStatus, deleteMovie } =
  useMoviesAPI()

onMounted(async () => {
  await fetchMovies()
})

function addMovie(movieData) {
  createMovie(movieData)
}
function archiveMovie(id) {
  updateMovieStatus(id, 'archived')
}
function restoreMovie(id) {
  updateMovieStatus(id, 'active')
}
function removeMovie(id) {
  deleteMovie(id)
}

const activeMovies = computed(() => movies.value.filter((m) => m.status === 'active'))
const archivedMovies = computed(() => movies.value.filter((m) => m.status === 'archived'))
const activeCount = computed(() => activeMovies.value.length)

provide('theme', theme)
provide('showToast', showToast)

provide('movies', {
  movies,
  activeMovies,
  archivedMovies,
  activeCount,
  addMovie,
  removeMovie,
  archiveMovie,
  restoreMovie,
})

function onChangeTab(tabId) {
  activeTab.value = tabId
}
</script>

<template>
  <div class="app">
    <AppHeader />
    <AppNavigation :active-tab="activeTab" @change-tab="onChangeTab" />

    <main class="app-main">
      <div v-if="loading" class="loading-indicator">⏳ Загрузка фильмов...</div>
      <div v-else-if="error" class="error-indicator">❌ Ошибка: {{ error }}</div>

      <Transition v-else name="fade" mode="out-in">
        <MovieList v-if="activeTab === 'movies'" key="movies" />
        <ArchiveList v-else-if="activeTab === 'archive'" key="archive" />
      </Transition>
    </main>

    <AppToast :toasts="toasts" @dismiss="removeToast" />
  </div>
</template>

<style scoped>
.loading-indicator {
  text-align: center;
  padding: 2rem;
  color: var(--color-text-muted);
}
.error-indicator {
  text-align: center;
  padding: 2rem;
  color: #ef4444;
}
</style>
