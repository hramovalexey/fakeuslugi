package com.fakeuslugi.seasonservice.dao;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    private long id;

    @NonNull
    @Column(nullable = false, unique = true)
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    private Collection<StatusHistory> statusHistory;
}
