package com.joey.musicplayer.repository;

import com.joey.musicplayer.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    Account findAccountByUsername(String username);
    Account findAccountByAccountId(int userId);
}
