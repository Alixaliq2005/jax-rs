package com.example.demo.dao.inter;

import com.example.demo.dto.request.AccountRequest;
import com.example.demo.entity.Account;

import java.util.List;

public interface AccountDaoInter {
    void create(AccountRequest accountRequest);
    public List<Account> findAllActiveAccounts(Long id);
}
