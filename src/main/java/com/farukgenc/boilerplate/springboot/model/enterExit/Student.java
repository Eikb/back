package com.farukgenc.boilerplate.springboot.model.enterExit;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STUDENTS")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer nr;
    private String name;
    @OneToMany
    private List<Entry> holidays;
    private String duty;
    private Boolean inBuilding;
    private String classRoom;
}
