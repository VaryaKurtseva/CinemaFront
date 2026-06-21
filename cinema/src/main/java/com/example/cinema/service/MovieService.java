package com.example.cinema.service;

import com.example.cinema.model.Movie;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Сервис для управления фильмами
 * Эмулирует работу с базой данных (хранение в памяти)
 */
@Service
public class MovieService {

    /**
     * Хранилище фильмов (эмуляция БД)
     */
    private final List<Movie> movies = new ArrayList<>();

    /**
     * Генератор ID
     */
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Конструктор - инициализация начальными данными
     * (эмуляция JSON-массива с бэкенда)
     */
    public MovieService() {
        initData();
    }

    /**
     * Начальные данные (эмуляция JSON-массива)
     */
    private void initData() {
        movies.addAll(List.of(
                new Movie(
                        idGenerator.getAndIncrement(),
                        "Интерстеллар",
                        "Кристофер Нолан",
                        "Фантастика",
                        169,
                        LocalDate.of(2014, 11, 7),
                        "active",
                        4.9
                ),
                new Movie(
                        idGenerator.getAndIncrement(),
                        "Паразиты",
                        "Пон Джун-хо",
                        "Драма",
                        132,
                        LocalDate.of(2019, 6, 5),
                        "active",
                        4.8
                ),
                new Movie(
                        idGenerator.getAndIncrement(),
                        "Безумный Макс: Дорога ярости",
                        "Джордж Миллер",
                        "Боевик",
                        120,
                        LocalDate.of(2015, 6, 12),
                        "archived",
                        4.7
                ),
                new Movie(
                        idGenerator.getAndIncrement(),
                        "Ла-Ла Ленд",
                        "Дэмьен Шазелл",
                        "Мелодрама",
                        128,
                        LocalDate.of(2016, 12, 22),
                        "active",
                        4.6
                ),
                new Movie(
                        idGenerator.getAndIncrement(),
                        "Оно",
                        "Энди Мускетти",
                        "Ужасы",
                        135,
                        LocalDate.of(2017, 9, 7),
                        "archived",
                        4.3
                ),
                new Movie(
                        idGenerator.getAndIncrement(),
                        "Джон Уик 4",
                        "Чад Стахелски",
                        "Боевик",
                        169,
                        LocalDate.of(2023, 3, 24),
                        "active",
                        4.7
                ),
                new Movie(
                        idGenerator.getAndIncrement(),
                        "Оппенгеймер",
                        "Кристофер Нолан",
                        "Драма",
                        180,
                        LocalDate.of(2023, 7, 21),
                        "active",
                        4.9
                )
        ));
    }

    /**
     * Получить все фильмы
     */
    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies);
    }

    /**
     * Получить фильм по ID
     */
    public Movie getMovieById(Long id) {
        return movies.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Создать новый фильм
     */
    public Movie createMovie(Movie movie) {
        movie.setId(idGenerator.getAndIncrement());
        movie.setStatus("active"); // новый фильм сразу в прокате
        movies.add(movie);
        return movie;
    }

    /**
     * Обновить фильм
     */
    public Movie updateMovie(Long id, Movie updatedMovie) {
        Movie existing = getMovieById(id);
        if (existing == null) {
            return null;
        }

        existing.setTitle(updatedMovie.getTitle());
        existing.setDirector(updatedMovie.getDirector());
        existing.setGenre(updatedMovie.getGenre());
        existing.setDuration(updatedMovie.getDuration());
        existing.setReleaseDate(updatedMovie.getReleaseDate());
        // Статус не обновляем через PUT, для этого есть отдельный метод

        return existing;
    }

    /**
     * Удалить фильм
     */
    public boolean deleteMovie(Long id) {
        return movies.removeIf(m -> m.getId().equals(id));
    }

    /**
     * Изменить статус фильма
     */
    public Movie changeStatus(Long id, String newStatus) {
        Movie movie = getMovieById(id);
        if (movie == null) {
            return null;
        }

        // Валидация статуса
        if ("active".equals(newStatus) || "archived".equals(newStatus)) {
            movie.setStatus(newStatus);
            return movie;
        }
        return null;
    }

    /**
     * Получить активные фильмы (в прокате)
     */
    public List<Movie> getActiveMovies() {
        return movies.stream()
                .filter(m -> "active".equals(m.getStatus()))
                .toList();
    }

    /**
     * Получить архивные фильмы
     */
    public List<Movie> getArchivedMovies() {
        return movies.stream()
                .filter(m -> "archived".equals(m.getStatus()))
                .toList();
    }

    /**
     * Получить количество активных фильмов
     */
    public long getActiveCount() {
        return movies.stream().filter(m -> "active".equals(m.getStatus())).count();
    }

    /**
     * Получить количество архивных фильмов
     */
    public long getArchivedCount() {
        return movies.stream().filter(m -> "archived".equals(m.getStatus())).count();
    }

}