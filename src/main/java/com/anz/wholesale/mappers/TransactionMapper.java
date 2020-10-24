package com.anz.wholesale.mappers;

import com.anz.wholesale.dtos.transaction.TransactionGetDto;
import com.anz.wholesale.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper {

    TransactionGetDto fromEntity(Transaction transaction);
}
