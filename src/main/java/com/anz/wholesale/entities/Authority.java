package com.anz.wholesale.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE)
    @Column(name = "id",updatable = false, nullable = false)
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "users_authorities",
            joinColumns = @JoinColumn(name = "authority_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<Authority> authorities;
}
