package com.joao.scorecouple.model;

import com.joao.scorecouple.core.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "workspaces")
public class Workspace implements DatabaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_users", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "workspace", fetch = FetchType.EAGER)
    private List<Coluna> colunas;
}
