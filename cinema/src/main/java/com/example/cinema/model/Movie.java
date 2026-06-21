package com.example.cinema.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Модель данных "Фильм"
 * Содержит 8 свойств (больше требуемых 5)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    /**
     * Уникальный идентификатор фильма
     */
    private Long id;

    /**
     * Название фильма
     */
    private String title;

    /**
     * Режиссёр фильма
     */
    private String director;

    /**
     * Жанр фильма
     */
    private String genre;

    /**
     * Длительность фильма (в минутах)
     */
    private Integer duration;

    /**
     * Дата премьеры
     */
    private LocalDate releaseDate;

    /**
     * Статус фильма:
     * "active"  - в прокате
     * "archived" - в архиве
     */
    private String status;

    /**
     * Рейтинг фильма (0.0 - 5.0)
     */
    private Double rating;


}