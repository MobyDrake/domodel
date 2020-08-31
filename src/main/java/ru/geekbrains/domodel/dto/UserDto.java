package ru.geekbrains.domodel.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Представление сущности пользователя для работы с фронтэндом
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    @NotBlank
    @Size(min = 5, message="Номер телефона не менее 5 символов")
    private String username;

    @NotBlank
    @Size(min = 5, message="Пароль должен быть не менее 5 символов")
    private String password;

    @NotBlank
    @Size(min = 5, message="Пароль должен быть не менее 5 символов")
    private String passwordConfirm;

    private String firstName;

    private String lastName;

    private String patronymic;

    @Email
    private String email;

    private String photoLink;

    private String address;
}