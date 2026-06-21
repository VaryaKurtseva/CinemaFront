import { ref } from 'vue'

const API_URL = 'http://localhost:8080/api/movies'

export function useMoviesAPI() {
  const movies = ref([])
  const loading = ref(false)
  const error = ref(null)

  async function fetchMovies() {
    loading.value = true
    error.value = null
    try {
      const response = await fetch(API_URL)
      if (!response.ok) throw new Error('Ошибка загрузки')
      movies.value = await response.json()
    } catch (err) {
      error.value = err.message
      console.error('Ошибка загрузки:', err)
    } finally {
      loading.value = false
    }
  }

  async function createMovie(movieData) {
    try {
      const response = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(movieData),
      })
      if (!response.ok) throw new Error('Ошибка создания')
      const newMovie = await response.json()
      movies.value.push(newMovie)
      return newMovie
    } catch (err) {
      error.value = err.message
      console.error('Ошибка создания:', err)
      return null
    }
  }

  async function updateMovieStatus(id, status) {
    try {
      const response = await fetch(`${API_URL}/${id}/status?status=${status}`, {
        method: 'PATCH',
      })
      if (!response.ok) throw new Error('Ошибка обновления статуса')
      const updated = await response.json()
      const index = movies.value.findIndex((m) => m.id === id)
      if (index !== -1) movies.value[index] = updated
      return updated
    } catch (err) {
      error.value = err.message
      console.error('Ошибка обновления:', err)
      return null
    }
  }

  async function deleteMovie(id) {
    try {
      const response = await fetch(`${API_URL}/${id}`, {
        method: 'DELETE',
      })
      if (!response.ok) throw new Error('Ошибка удаления')
      movies.value = movies.value.filter((m) => m.id !== id)
      return true
    } catch (err) {
      error.value = err.message
      console.error('Ошибка удаления:', err)
      return false
    }
  }

  return {
    movies,
    loading,
    error,
    fetchMovies,
    createMovie,
    updateMovieStatus,
    deleteMovie,
  }
}
