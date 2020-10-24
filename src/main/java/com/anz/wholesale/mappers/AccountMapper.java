package com.anz.wholesale.mappers;

import com.anz.wholesale.dtos.account.AccountGetDto;
import com.anz.wholesale.dtos.account.AccountPostDto;
import com.anz.wholesale.dtos.account.AccountPutDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    Actor toEntity(AccountPostDto actorPostDto);

    AccountGetDto fromEntity(Actor person);

    void copy(AccountPutDto actorPutDto, @MappingTarget Actor actor);
}
