package com.example.cinema.controller;


import com.example.cinema.model.Movie;
import com.example.cinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления фильмами
 * Базовый URL: /api/movies
 */
@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "*") // Разрешаем запросы с любого источника (для Vue)
public class MovieController {

    @Autowired
    private MovieService movieService;

    // ============================================================
    // GET-запросы
    // ============================================================

    /**
     * GET /api/movies
     * Получить все фильмы
     */
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    /**
     * GET /api/movies/active
     * Получить только активные фильмы (в прокате)
     */
    @GetMapping("/active")
    public ResponseEntity<List<Movie>> getActiveMovies() {
        return ResponseEntity.ok(movieService.getActiveMovies());
    }

    /**
     * GET /api/movies/archived
     * Получить только архивные фильмы
     */
    @GetMapping("/archived")
    public ResponseEntity<List<Movie>> getArchivedMovies() {
        return ResponseEntity.ok(movieService.getArchivedMovies());
    }

    /**
     * GET /api/movies/{id}
     * Получить фильм по ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        if (movie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movie);
    }

    /**
     * GET /api/movies/stats
     * Получить статистику (количество фильмов)
     */
    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats() {
        StatsResponse stats = new StatsResponse(
            movieService.getAllMovies().size(),
            movieService.getActiveCount(),
            movieService.getArchivedCount()
        );
        return ResponseEntity.ok(stats);
    }

    // ============================================================
    // POST-запросы
    // ============================================================

    /**
     * POST /api/movies
     * Создать новый фильм
     */
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        // Валидация
        if (movie.getTitle() == null || movie.getTitle().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (movie.getDirector() == null || movie.getDirector().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if (movie.getReleaseDate() == null) {
            return ResponseEntity.badRequest().build();
        }

        Movie created = movieService.createMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ============================================================
    // PUT-запросы
    // ============================================================

    /**
     * PUT /api/movies/{id}
     * Полное обновление фильма
     */
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(
            @PathVariable Long id,
            @RequestBody Movie movie) {
        Movie updated = movieService.updateMovie(id, movie);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // ============================================================
    // PATCH-запросы
    // ============================================================

    /**
     * PATCH /api/movies/{id}/status?status=archived
     * Изменить статус фильма (активный ⇄ архив)
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Movie> changeStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        Movie updated = movieService.changeStatus(id, status);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // ============================================================
    // DELETE-запросы
    // ============================================================

    /**
     * DELETE /api/movies/{id}
     * Удалить фильм
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        boolean deleted = movieService.deleteMovie(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // ============================================================
    // Вспомогательный класс для статистики
    // ============================================================

    private static class StatsResponse {
        private final long total;
        private final long active;
        private final long archived;

        public StatsResponse(long total, long active, long archived) {
            this.total = total;
            this.active = active;
            this.archived = archived;
        }

        public long getTotal() { return total; }
        public long getActive() { return active; }
        public long getArchived() { return archived; }
    }
}