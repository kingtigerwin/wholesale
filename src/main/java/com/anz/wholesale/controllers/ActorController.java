package com.anz.wholesale.controllers;

import com.anz.wholesale.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actors")
@RequiredArgsConstructor
public class ActorController {

    private final AccountService accountService;

//    @GetMapping
//    public ResponseEntity<List<ActorGetDto>> find() {
//        return ResponseEntity.ok(actorService.getAllActors());
//    }
}
