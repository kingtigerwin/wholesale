package com.anz.wholesale.mappers;

import com.anz.wholesale.dtos.account.AccountGetDto;
import com.anz.wholesale.dtos.account.AccountPostDto;
import com.anz.wholesale.entities.Account;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    Account toEntity(AccountPostDto actorPostDto);

    AccountGetDto fromEntity(Account account);
}
