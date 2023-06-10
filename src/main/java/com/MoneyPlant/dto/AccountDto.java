package com.MoneyPlant.dto;

import com.MoneyPlant.user.Account;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private String firstName;

    private String lastName;

    private String email;

    public static final AccountDto convertToDto(Account account) {
        return AccountDto.builder()
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .build();
    }
}
