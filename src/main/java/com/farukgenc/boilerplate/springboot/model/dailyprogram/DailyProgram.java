package com.farukgenc.boilerplate.springboot.model.dailyprogram;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PROGRAM")
public class DailyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String program;

    private Boolean active;

    private String time;



}
