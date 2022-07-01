package com.fakeuslugi.seasonservice.dao;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class StatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "status_history_generator")
    private long id;

    @NonNull
    @Column(nullable = false)
    private ZonedDateTime timestamp;

    private String executorComment;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ProvidedService providedService;

    @NonNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Status status;

}
