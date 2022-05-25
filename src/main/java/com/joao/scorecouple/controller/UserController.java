package com.joao.scorecouple.controller;

import com.joao.scorecouple.core.BasicRepository;
import com.joao.scorecouple.core.DynamicDto;
import com.joao.scorecouple.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private EntityManager em;

    @Autowired
    private BasicRepository repository;

    @PostMapping
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ResponseEntity<Object> create(@RequestBody User user) {
        User newUser = repository.save(user);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("{email}")
    public ResponseEntity<Object> create(@PathVariable(name = "email") String email) {
        User user = repository.findOne(User.class, QUser.user.email.eq(email));
        Workspace workspace = repository.findOne(Workspace.class, QWorkspace.workspace.user().id.eq(user.getId()));
        DynamicDto dtoWorkSpace = DynamicDto.of();
        if (Objects.nonNull(workspace)) {
            List<DynamicDto> dtoColunas = workspace.getColunas()
                    .stream()
                    .map(c -> DynamicDto.of(c, false)
                            .with("id", c.getId())
                            .with("title", c.getTitle())
                            .with("movies", c.getMovies())
                    ).collect(Collectors.toList());

            dtoWorkSpace = DynamicDto.of(workspace, false)
                    .with("id", workspace.getId())
                    .with("colunas", dtoColunas);
        }

        DynamicDto dto = DynamicDto.of(user, false)
                .with("id", user.getId())
                .with("email", user.getEmail())
                .with("username", user.getUsername())
                .with("workspace", dtoWorkSpace);
        return ResponseEntity.ok(dto);
    }
}
