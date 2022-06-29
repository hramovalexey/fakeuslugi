package com.fakeuslugi.seasonservice.dao;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class SeasonService {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "season_service_generator")
    private long id;

    @NonNull
    @Column (nullable = false, unique = true)
    private String name;

    @NonNull
    @Column (nullable = false)
    private long serviceLimit;

    @OneToMany(mappedBy = "seasonService")
    private Collection<ProvidedService> providedService;
}
