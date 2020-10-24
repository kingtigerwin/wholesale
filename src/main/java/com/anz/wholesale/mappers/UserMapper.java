package com.anz.wholesale.mappers;

import com.anz.wholesale.dtos.user.UserGetDto;
import com.anz.wholesale.dtos.user.UserPostDto;
import com.anz.wholesale.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserPostDto userPostDto);

    UserGetDto fromEntity(User user);
}
