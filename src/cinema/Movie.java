package cinema;

import java.time.LocalDate;

public class Movie {
    private final String title;
    private final String director;
    private final String[] starringCast;
    private final String filmRating;
    private final int movieLength;
    private final String genre;
    private final LocalDate releaseDate;
    private String synopsis;

    Movie(String title,
          String director,
          String[] starringCast,
          String filmRating,
          int movieLength,
          String genre,
          LocalDate releaseDate) {
        this.title = title;
        this.director = director;
        this.starringCast = starringCast;
        this.filmRating = filmRating;
        this.movieLength = movieLength;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }
}


