package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;

import java.util.Optional;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;

@Service
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    //user registration
    public Account userRegistration(Account account){
        Optional<Account> existingAccount = accountRepository.findByUsername(account.getUsername());
        if (account.getUsername() != null && account.getUsername() != "" && account.getPassword().length() >= 4 && existingAccount.isEmpty()){
            return accountRepository.save(account); 
        } else if (existingAccount.isPresent()){
            throw new DuplicateUsernameException();
        } else {
            return null;
        }
    }

    //login
    public Account login(Account account){
        Optional<Account> loginAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(loginAccount.isPresent()){
            return loginAccount.get();
        } else {
            return null;
        }
    }

}
