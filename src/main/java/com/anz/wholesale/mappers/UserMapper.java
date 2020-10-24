package com.anz.wholesale.mappers;

import com.anz.wholesale.dtos.person.PersonGetDto;
import com.anz.wholesale.dtos.person.PersonPutDto;
import com.anz.wholesale.dtos.user.UserPostDto;
import com.anz.wholesale.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserPostDto userPostDto);

    PersonGetDto fromEntity(User user);

    void copy(PersonPutDto personPutDto, @MappingTarget User user);
}
