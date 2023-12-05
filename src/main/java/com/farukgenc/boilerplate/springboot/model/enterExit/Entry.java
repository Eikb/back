package com.farukgenc.boilerplate.springboot.model.enterExit;

import com.farukgenc.boilerplate.springboot.model.Employee;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ENTRY")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long dutyId;

    private LocalDateTime enter;
    private LocalDateTime exit;

    private String permissionGiverName;

    private String studentName;
    private String reason;

}
