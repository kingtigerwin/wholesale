package com.anz.wholesale.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE)
    @Column(name = "id",updatable = false, nullable = false)
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private String username;

    @Column(name = "encoded_password", unique = false, nullable = false)
    private String encodedPassword;

    @ManyToMany
    @JoinTable(name = "users_authorities",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;
}
