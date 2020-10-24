package com.anz.wholesale.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString(exclude = "users")
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false, nullable = false)
    private Long id;

    @Column(name = "permission", unique = true, nullable = false)
    private String permission;

    @ManyToMany
    @JoinTable(name = "users_authorities",
            joinColumns = @JoinColumn(name = "authority_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;
}
