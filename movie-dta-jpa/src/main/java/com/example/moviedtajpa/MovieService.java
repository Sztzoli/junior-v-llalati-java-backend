package com.example.moviedtajpa;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final ModelMapper mapper;


    public List<MovieDto> listMovies() {
        List<Movie> all = movieRepository.findAll();
        Type targetListType = new TypeToken<List<MovieDto>>() {
        }.getType();
        return mapper.map(all, targetListType);
    }

    public MovieDto createFilm(CreateFilmCommand command) {
        Movie save = movieRepository.save(new Movie(command.getTitle()));
        return mapper.map(save, MovieDto.class);
    }

    @Transactional
    public MovieDto addRating(Long id, AddRatingCommand command) {
        Movie movie = findById(id);
        movie.addRating(command.getRating());
        return mapper.map(movie, MovieDto.class);
    }

    private Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
    }
}
