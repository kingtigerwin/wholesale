package com.anz.wholesale.services;

import com.anz.wholesale.dtos.account.AccountGetDto;
import com.anz.wholesale.mappers.AccountMapper;
import com.anz.wholesale.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    public List<AccountGetDto> getAllActors() {
        return accountRepository.findAll().stream()
                .map(actor -> accountMapper.fromEntity(actor))
                .collect(Collectors.toList());
    }
}
