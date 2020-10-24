package com.anz.wholesale.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Actor, Long> {

    List<Actor> findByFirstnameOrLastname(String firstname, String lastname);


}
