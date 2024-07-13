package com.worstmovie.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "movies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "release_year")
    private Integer year;

    private String title;

    private String studio;

    private String producer;

    private Boolean winner;
}
