package com.joao.scorecouple.controller;

import com.joao.scorecouple.core.BasicRepository;
import com.joao.scorecouple.core.DynamicDto;
import com.joao.scorecouple.model.Coluna;
import com.joao.scorecouple.model.Movie;
import com.joao.scorecouple.model.QColuna;
import com.joao.scorecouple.model.QMovie;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/columns")
@CrossOrigin("*")
public class ColumnController {

    @Autowired
    private EntityManager em;

    @Autowired
    private BasicRepository repository;

    @PostMapping
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ResponseEntity<Object> create(@RequestBody Coluna coluna) {
        Coluna newColuna = repository.save(coluna);
        return ResponseEntity.ok(newColuna);
    }

    @PutMapping("{id}")
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ResponseEntity<Object> update(@PathVariable(name = "id") Integer id, @RequestBody Coluna column) {
        Coluna findedColuna = repository.findOne(Coluna.class, QColuna.coluna.id.eq(id));
        BeanUtils.copyProperties(column, findedColuna);
        repository.save(findedColuna);
        return ResponseEntity.ok(findedColuna);
    }

    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<DynamicDto> colunas = repository.findAll(Coluna.class)
                .stream()
                .map(coluna -> {
                    return DynamicDto.of(coluna)
                            .with("id", coluna.getId())
                            .with("title", coluna.getTitle())
                            .with("movies", coluna.getMovies()
                                    .stream()
                                    .sorted(Comparator.comparing(Movie::getName))
                                    .collect(Collectors.toList())
                            );
                }).collect(Collectors.toList());

        return ResponseEntity.ok(colunas);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findOne(@PathVariable(name = "id") Integer columnID) {
        Coluna coluna = repository.findOne(Coluna.class, QColuna.coluna.id.eq(columnID));
        return ResponseEntity.ok(coluna);
    }

    @DeleteMapping("{id}")
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ResponseEntity<Object> delete(@PathVariable(name = "id") Integer columnID) {
        Coluna coluna = repository.findOne(Coluna.class, QColuna.coluna.id.eq(columnID));
        repository.remove(coluna);
        return ResponseEntity.ok().build();
    }

    public boolean hasCards(Coluna coluna) {
        return repository.exists(Movie.class, QMovie.movie.column().id.eq(coluna.getId()));
    }
}
