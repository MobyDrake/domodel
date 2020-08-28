package ru.geekbrains.domodel.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.domodel.entities.UserRepresentation;
import ru.geekbrains.domodel.services.api.NewsService;
import ru.geekbrains.domodel.services.api.UserService;

import javax.validation.Valid;
import java.security.Principal;

import static ru.geekbrains.domodel.entities.constants.Messages.*;

/**
 * Главный контроллер web-приложения
 */
@Controller
@RequiredArgsConstructor
public class MainController {

    // Имена шаблонов страниц
    private static final String REGISTER_FORM = "register";

    // Необходимые сервисы
    private final UserService userService;
    private final NewsService newsService;

    /**
     * Перехват запроса главной страницы
     */
    @GetMapping("")
    public String getHomePage(@RequestParam(required = false) String error, Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        model.addAttribute("lastNews", newsService.getLastNews());
        return "index";
    }

    /**
     * Перехват запроса регистрации нового пользователя
     */
    @GetMapping("/register")
    public String getRegisterPage(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        model.addAttribute("userData", new UserRepresentation());
        return REGISTER_FORM;
    }

    /**
     * Перехват запроса создания нового пользователя
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userData") UserRepresentation userData,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return REGISTER_FORM;
        }

        if (!userData.getPassword().equals(userData.getPasswordConfirm())) {
            bindingResult.rejectValue("password", "", PASSWORD_MISMATCH);
            return REGISTER_FORM;
        }

        if (userService.createUser(userData) != null) {
            model.addAttribute("message",
                    String.format(USER_CREATED, userData.getUsername()));
        } else {
            bindingResult.rejectValue("username", "",
                    String.format(USER_HAS_ALREADY_CREATED, userData.getUsername()));
        }
        return REGISTER_FORM;
    }
}
