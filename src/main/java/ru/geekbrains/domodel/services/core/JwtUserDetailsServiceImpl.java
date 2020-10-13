package ru.geekbrains.domodel.services.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.geekbrains.domodel.entities.common.JwtUser;
import ru.geekbrains.domodel.services.api.UserService;

import static ru.geekbrains.domodel.entities.constants.Messages.USER_FOUND;
import static ru.geekbrains.domodel.entities.constants.Messages.USER_NOT_FOUND;

/**
 * Сервис, отвечающий за авторизацию пользователей.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    // Необходимые сервисы
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUser user = userService.getJwtUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND, username));
        }
        log.info(String.format(USER_FOUND, username));
        return user;
    }
}
