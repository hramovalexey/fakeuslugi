package com.fakeuslugi;



import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

// @NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
@Entity
@Data
@RequiredArgsConstructor
public class TestEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exchange_generator")
    @Id
    @Column
    private long testField;

    @Column
    private String content;

}
