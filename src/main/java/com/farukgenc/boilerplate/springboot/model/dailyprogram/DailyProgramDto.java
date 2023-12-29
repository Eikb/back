package com.farukgenc.boilerplate.springboot.model.dailyprogram;

import lombok.*;

import javax.persistence.Entity;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyProgramDto {
    private DailyProgram dailyProgram;
    private List<Integer> days;

}
