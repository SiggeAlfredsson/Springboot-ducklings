package com.alfredsson.demo.service;

import com.alfredsson.demo.repo.AccountRepository;

import java.sql.SQLException;

public class AccountService {

    private AccountRepository accountRepository;

    public AccountService() {
        this.accountRepository = new AccountRepository();
    }

    public boolean login(String username, String password) {
        if (accountRepository.login(username, password)) {
            return true;
        }
        return false;
    }

    public boolean usernameExists(String username) {
       if (accountRepository.usernameExists(username)){
           return true;
       }
        return false;
    }

    public Object getPasswordByUsername(String username) throws SQLException {
       return accountRepository.getPasswordByUsername(username);
    }

    public void updatePassword(String username, String password) {
        accountRepository.updatePassword(username, password);
    }
}
