package com.example.demo.dto.response;

import com.example.demo.entity.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private String accountNumber;

    private AccountStatus accountStatus;

    private Double accountBalance;
}
