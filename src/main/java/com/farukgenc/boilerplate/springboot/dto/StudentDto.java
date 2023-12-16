package com.farukgenc.boilerplate.springboot.dto;

import com.farukgenc.boilerplate.springboot.model.enterExit.Entry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Integer nr;
    private String name;
    private List<Entry> holidays;
    private String duty;
    private Boolean inBuilding;
    private String classRoom;
}
