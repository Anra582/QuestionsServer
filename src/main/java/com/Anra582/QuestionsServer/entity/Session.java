package com.Anra582.QuestionsServer.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String fio;

    @Column
    private LocalDateTime localDateTime;

    @Column
    private Double percent;
}
