package com.fakeuslugi.seasonservice.dao;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.Collection;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class SeasonService implements SeasonServiceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "season_service_generator")
    private long id;

    @NonNull
    @Column (nullable = false, unique = true)
    private String name;

    @NonNull
    @Positive(message = "service limit value must be positive")
    @Column (nullable = false)
    private long serviceLimit;

    @ToString.Exclude
    @OneToMany(mappedBy = "seasonService", fetch = FetchType.LAZY)
    private Collection<ProvidedService> providedService;

}
