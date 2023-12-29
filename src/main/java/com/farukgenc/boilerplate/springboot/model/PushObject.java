package com.farukgenc.boilerplate.springboot.model;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PushObject {

    private String to;
    private String sound;
    private String title;
    private String body;

}
