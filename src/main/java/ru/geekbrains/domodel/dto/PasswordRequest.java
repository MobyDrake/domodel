package ru.geekbrains.domodel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PasswordRequest {

    private String username;

    private String oldPassword;

    private String newPassword;

    private String newPasswordConfirm;
}
