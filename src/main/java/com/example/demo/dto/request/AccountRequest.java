package com.example.demo.dto.request;

import com.example.demo.entity.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private Long id;
    private String accountNumber;

    private AccountStatus accountStatus;

    private Double accountBalance;
}
