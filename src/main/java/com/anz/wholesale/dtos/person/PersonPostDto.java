package com.anz.wholesale.dtos.person;

import lombok.Data;

@Data
public class PersonPostDto {
    private String name;
    private String password;
    private int age;
    private String gender;
}
