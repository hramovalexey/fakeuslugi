package com.fakeuslugi.seasonservice.dao;

import com.fakeuslugi.seasonservice.exception.SeasonServiceException;
import com.fakeuslugi.security.dao.Customer;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@Slf4j
public class ProvidedService implements OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "provided_service_generator")
    private long id;

    @Column
    private String userComment;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(nullable = false)
    private SeasonService seasonService;

    @ToString.Exclude
    @OneToMany(mappedBy = "providedService", fetch = FetchType.LAZY)
    @OrderBy("timestamp ASC")
    private List<StatusHistory> statusHistory;

    @Override
    public ZonedDateTime getTimestamp() {
        if (statusHistory != null && statusHistory.size() > 0) {
            return statusHistory.get(0).getTimestamp();
        }
        throw new SeasonServiceException("Incorrect call to getTimestamp. statusHistory is null or empty", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public String getName() {
        if (seasonService != null) {
            return seasonService.getName();
        }
        throw new SeasonServiceException("Incorrect call to getName. seasonService is null", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
