package com.fakeuslugi.seasonservice.dao;

import com.fakeuslugi.seasonservice.exception.SeasonServiceException;
import lombok.*;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class StatusHistory implements StatusHistoryInfo {

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

    @Override
    public String getStatusName() {
        if (status != null) {
            return status.getName();
        }
        throw new SeasonServiceException("Incorrect call to getStatusName. status is null", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
