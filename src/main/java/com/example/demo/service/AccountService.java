package com.example.demo.service;

import com.example.demo.dto.request.AccountRequest;
import com.example.demo.dto.request.TransferAccount;
import com.example.demo.dto.response.AccountResponse;

import java.util.List;

public interface AccountService {
    void createAccount(AccountRequest accountRequest);
    List<AccountResponse> findAllActiveAccounts(Long id);
    void transferFunds(TransferAccount transferAccount);
}
