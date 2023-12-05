package com.farukgenc.boilerplate.springboot.model;

import com.farukgenc.boilerplate.springboot.model.enterExit.Entry;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DUTIES")
public class Duty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private LocalDateTime startedTime;
    private LocalDateTime completedTime;
    private Boolean completed;

    private Long firstGuardId;

    private Long secondGuardId;
    private String firstGuardName;

    private String secondGuardName;

    private Boolean active;

    @ManyToMany
    private List<Entry> entries;

}
