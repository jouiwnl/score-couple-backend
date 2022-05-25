package com.joao.scorecouple.controller;

import com.joao.scorecouple.core.BasicRepository;
import com.joao.scorecouple.model.QUser;
import com.joao.scorecouple.model.User;
import com.joao.scorecouple.model.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/workspaces")
@CrossOrigin("*")
public class WorkspaceController {

    @Autowired
    private EntityManager em;

    @Autowired
    private BasicRepository repository;

    @PostMapping("{userId}")
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Workspace createByUser(@PathVariable(name = "userId") Integer userId) {
        User findedUser = repository.findOne(User.class, QUser.user.id.eq(userId));
        Workspace workspace = Workspace.builder()
                .user(findedUser)
                .build();
        return repository.save(workspace);
    }
}
