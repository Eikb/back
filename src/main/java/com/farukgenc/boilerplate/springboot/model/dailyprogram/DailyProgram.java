package com.farukgenc.boilerplate.springboot.model.dailyprogram;

import lombok.*;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

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

    private String courseId;

    private Integer day;
    private String startTime;
    private String endTime;
    private String color;


}
