package com.anz.wholesale.repositories;

import com.anz.wholesale.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select acc from Account acc join fetch acc.transactions  where acc.id=:accountId")
    Account findAccountAndRelatedTransactionsByAccountId(@Param("accountId") Long accountId);
}
