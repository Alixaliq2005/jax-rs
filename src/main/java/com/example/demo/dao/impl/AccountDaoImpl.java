package com.example.demo.dao.impl;

import com.example.demo.dao.inter.AccountDaoInter;
import com.example.demo.dto.request.AccountRequest;
import com.example.demo.entity.Account;
import com.example.demo.entity.AccountStatus;
import com.example.demo.entity.User;
import lombok.Data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Data
public class AccountDaoImpl implements AccountDaoInter {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory ("company");
    private static final EntityManager em = emf.createEntityManager ();

    @Override
    public void create(AccountRequest accountRequest) {
        em.getTransaction ().begin ();
        User user = em.find (User.class, accountRequest.getId ());
        Account account = Account.builder ()
                .accountNumber (accountRequest.getAccountNumber ())
                .accountStatus (accountRequest.getAccountStatus ())
                .accountBalance (accountRequest.getAccountBalance ())
                .user (user)
                .build ();
        System.out.println (account);
        em.persist (account);
        em.getTransaction ().commit ();
    }

    @Override
    public List<Account> findAllActiveAccounts(Long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> cq = cb.createQuery(Account.class);
        Root<Account> root = cq.from(Account.class);

        cq.select(root).where(
                cb.equal(root.get("accountStatus"), AccountStatus.ACTIVE),
                cb.equal(root.get("user").get("id"), id)
        );

        return em.createQuery(cq).getResultList();

    }

}
