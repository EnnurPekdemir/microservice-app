package com.haydikodlayalim.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto implements Serializable {

    private String id;
    private String username;
    private String email;
    private String passwd;
}
