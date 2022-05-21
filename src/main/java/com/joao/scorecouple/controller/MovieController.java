package com.joao.scorecouple.controller;

import com.joao.scorecouple.core.BasicRepository;
import com.joao.scorecouple.model.Coluna;
import com.joao.scorecouple.model.Movie;
import com.joao.scorecouple.model.QColuna;
import com.joao.scorecouple.model.QMovie;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/movies")
@CrossOrigin("*")
public class MovieController {

    @Autowired
    private EntityManager em;

    @Autowired
    private BasicRepository repository;

    @PostMapping
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ResponseEntity<Object> create(@RequestBody Movie movie,
                                         @RequestParam(name = "columnid") Integer columnId) {
        Coluna coluna = repository.findOne(Coluna.class, QColuna.coluna.id.eq(columnId));
        movie.setColumn(coluna);

        Movie newMovie = repository.save(movie);

        return ResponseEntity.ok(newMovie);
    }

    @PutMapping("{id}")
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ResponseEntity<Object> update(@RequestBody Movie movie,
                                         @PathVariable(name = "id") Integer movieId) {
        Coluna coluna = repository.findOne(Coluna.class, QColuna.coluna.id.eq(movie.getColumnId()));
        Movie findedMovie = repository.findOne(Movie.class, QMovie.movie.id.eq(movieId));
        BeanUtils.copyProperties(movie, findedMovie);
        findedMovie.setColumn(coluna);

        repository.save(findedMovie);

        return ResponseEntity.ok(findedMovie);
    }

    @DeleteMapping("{id}")
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ResponseEntity<Object> delete(@PathVariable(name = "id") Integer movieID) {
        Movie movie = repository.findOne(Movie.class, QMovie.movie.id.eq(movieID));
        repository.remove(movie);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findOne(@PathVariable(name = "id") Integer movieID) {
        Movie movie = repository.findOne(Movie.class, QMovie.movie.id.eq(movieID));

        return ResponseEntity.ok(movie);
    }
}
