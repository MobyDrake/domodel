package ru.geekbrains.domodel.services.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.geekbrains.domodel.entities.common.JwtUser;
import ru.geekbrains.domodel.services.api.UserService;

import static ru.geekbrains.domodel.entities.constants.Messages.USER_NOT_FOUND_BY_USERNAME;

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
        JwtUser user = userService.getJwtByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_USERNAME, username));
        }
        return user;
    }
}
