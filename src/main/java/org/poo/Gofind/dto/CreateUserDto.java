package org.poo.Gofind.dto;

import lombok.Data;

@Data
public class CreateUserDto {
    private long id;
    private String nom;
    private String email;
    private String password;
}
