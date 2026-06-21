import { ref, watch } from 'vue'

const STORAGE_KEY = 'cinema-theme'

export function useTheme() {
  const isDark = ref(localStorage.getItem(STORAGE_KEY) === 'dark')

  watch(
    isDark,
    (dark) => {
      localStorage.setItem(STORAGE_KEY, dark ? 'dark' : 'light')
      document.documentElement.classList.toggle('theme-dark', dark)
    },
    { immediate: true },
  )

  function toggleTheme() {
    isDark.value = !isDark.value
  }

  return { isDark, toggleTheme }
}
