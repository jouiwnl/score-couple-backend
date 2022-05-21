package com.joao.scorecouple.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.joao.scorecouple.core.DatabaseEntity;
import com.joao.scorecouple.enums.Status;
import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie implements DatabaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Formula("id_columns")
    private Integer columnId;

    @Column(name = "name")
    private String name;

    @Column(name = "score")
    private Integer score;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.NOTSTARTED;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "runtime")
    private Integer runtime;

    @Column(name = "movie_description")
    private String movieDescription;

    @Column(name = "genre")
    private String genre;

    @Column(name = "date_to_see")
    private String dateToSee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_columns", referencedColumnName = "id")
    @JsonIgnore
    private Coluna column;
}
