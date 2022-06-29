package com.fakeuslugi.seasonservice.dao;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@RequiredArgsConstructor
// @NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class StatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "status_history_generator")
    private long id;

    @Column(nullable = false)
    private ZonedDateTime timestamp;

    private String executorComment;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ProvidedService providedService;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Status status;

}
