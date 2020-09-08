package ru.geekbrains.domodel.dto;

import lombok.Data;

/**
 * Запрос авторизации
 */
@Data
public class AuthenticationRequest {

    private String username;

    private String password;
}
