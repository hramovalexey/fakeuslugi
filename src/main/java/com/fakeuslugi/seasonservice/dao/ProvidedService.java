package com.fakeuslugi.seasonservice.dao;

import com.fakeuslugi.security.dao.Customer;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@RequiredArgsConstructor
// @NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class ProvidedService {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "provided_service_generator")
    private long userId;

    @Column
    private String userComment;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(nullable = false)
    private SeasonService seasonService;

    @OneToMany(mappedBy = "providedService")
    private Collection<StatusHistory> statusHistory;



}
