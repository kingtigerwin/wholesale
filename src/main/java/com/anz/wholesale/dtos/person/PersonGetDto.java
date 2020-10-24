package com.anz.wholesale.dtos.person;

import lombok.Data;

import java.util.UUID;

@Data
public class PersonGetDto {

    private UUID id;
    private String name;
    private int age;
    private String gender;
}
