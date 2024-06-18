package com.example.demo.service;

import com.example.demo.dao.impl.AccountDaoImpl;
import com.example.demo.dto.request.AccountRequest;
import com.example.demo.dto.request.TransferAccount;
import com.example.demo.dto.response.AccountResponse;
import com.example.demo.entity.Account;
import com.example.demo.entity.AccountStatus;
import com.example.demo.exception.CustomException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

public class AccountServiceImpl implements AccountService {
    private final AccountDaoImpl accountDao = new AccountDaoImpl ();

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory ("company");
    private static final EntityManager em = emf.createEntityManager ();


    @Override
    @Transactional
    public void createAccount(AccountRequest accountRequest) {
        if (!accountRequest.getAccountNumber ().matches ("^[A-Z]{2}\\d{14}$")) {
            throw new CustomException ("Invalid account number format.", Response.Status.BAD_REQUEST.getStatusCode ());
        }
        accountDao.create (accountRequest);
    }

    @Override
    public List<AccountResponse> findAllActiveAccounts(Long id) {
        List<Account> accounts = accountDao.findAllActiveAccounts (id);
        return accounts.stream ().map (this::mapToAccountResponse).collect (Collectors.toList ());
    }

    private AccountResponse mapToAccountResponse(Account account) {
        AccountResponse response = new AccountResponse ();
        response.setAccountNumber (account.getAccountNumber ());
        response.setAccountBalance (account.getAccountBalance ());
        response.setAccountStatus (account.getAccountStatus ());
        return response;
    }

    @Override
    public void transferFunds(TransferAccount transferAccount) {
        em.getTransaction ().begin ();
        Account fromAccount = em.find (Account.class, transferAccount.getFromAccountId ());
        Account toAccount = em.find (Account.class, transferAccount.getToAccountId ());
        if (fromAccount == null || toAccount == null) {
            throw new CustomException ("Account not found.", Response.Status.NOT_FOUND.getStatusCode ());
        }
        if (fromAccount.getAccountStatus () != AccountStatus.ACTIVE || toAccount.getAccountStatus () != AccountStatus.ACTIVE) {
            throw new CustomException ("Cannot transfer to/from an inactive account.", Response.Status.BAD_REQUEST.getStatusCode ());
        }
        if (fromAccount.getId ().equals (toAccount.getId ())) {
            throw new CustomException ("Cannot transfer to the same account.", Response.Status.BAD_REQUEST.getStatusCode ());
        }
        if (fromAccount.getAccountBalance () < transferAccount.getAmount ()) {
            throw new CustomException ("Insufficient balance.", Response.Status.BAD_REQUEST.getStatusCode ());
        }

        fromAccount.setAccountBalance (fromAccount.getAccountBalance () - transferAccount.getAmount ());
        toAccount.setAccountBalance (toAccount.getAccountBalance () + transferAccount.getAmount ());

        em.merge (fromAccount);
        em.merge (toAccount);
        em.getTransaction ().commit ();


    }
}


