package com.example.demo.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Builder
//@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Table(name= "user_account")
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    @Size(min = 16, max = 16, message = "Account number must be 16 characters long")
    @Pattern(regexp = "^[A-Z]{2}\\d{14}$", message = "Account number must start with 2 uppercase letters followed by 14 digits")
    String accountNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Enumerated(EnumType.STRING)
    AccountStatus accountStatus;

    Double accountBalance;

//    public Account() {
//
//    }


    @PrePersist
    public void prePersist() {
        this.accountStatus = AccountStatus.ACTIVE;
        this.accountBalance = 0.0;
    }


}
