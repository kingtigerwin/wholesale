package com.anz.wholesale.controllers;

import com.anz.wholesale.dtos.account.AccountGetDto;
import com.anz.wholesale.dtos.account.AccountPostDto;
import com.anz.wholesale.dtos.account.AccountPutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/management/accounts")
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AccountManagementController {

    private static final List<AccountGetDto> ACCOUNTS = List.of(
            new AccountGetDto("12345","SGSavings","Current",LocalDate.now(), "AUD",12.22),
            new AccountGetDto("23432","AUGSavings","Savings",LocalDate.now(), "SGD",23.44),
            new AccountGetDto("45634","AUGCurrent","Current",LocalDate.now(), "SGD",11.11)
    );


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
    public List<AccountGetDto> getAllAccounts() {
        return ACCOUNTS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('account:write')")
    public void addAccount(AccountPostDto accountPostDto) {
        System.out.println(accountPostDto);
    }

    @DeleteMapping(path = "{accountId}")
    @PreAuthorize("hasAuthority('account:write')")
    public void deleteAccount(@PathVariable("accountId") Long accountId) {
        System.out.println(accountId);
    }

    @PutMapping(path = "{accountId}")
    @PreAuthorize("hasAuthority('account:write')")
    public void updateAccount(@PathVariable("accountId") Long accountId, @RequestBody AccountPutDto accountPutDto){
        System.out.println(String.format("%s %s", accountId, accountPutDto));
    }
}
