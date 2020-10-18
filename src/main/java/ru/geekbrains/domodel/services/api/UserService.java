package ru.geekbrains.domodel.services.api;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.geekbrains.domodel.dto.PasswordRequest;
import ru.geekbrains.domodel.dto.UserDto;
import ru.geekbrains.domodel.dto.UserRequest;
import ru.geekbrains.domodel.entities.User;
import ru.geekbrains.domodel.entities.common.JwtUser;

import java.util.List;

/**
 * Сервис пользователей
 */
@Service
public interface UserService {

    /*
     * СОГЛАШЕНИЕ О НАИМЕНОВАНИИ МЕТОДОВ СЕРВИСОВ
     * User getById(Long id) найти объект по параметру
     * UserDto getDtoById(Long id) найти Dto объект по параметру
     * List<User> getAll() найти все объекты
     * List<UserDto> getAllDto() найти все Dto объекты
     * List<UserDto> getAllDtoByUser(UserDto userDto) найти все Dto объекты по параметру
     * UserDto update(UserDto userDto) изменить объект
     * UserDto save(UserDto userDto) сохранить объект
     * List<UserDto> saveAllDto(List<UserDto> userDtoList) сохранить список объектов
     * void delete(UserDto UserDto) удалить конкретный объект
     * void deleteById(Long id) удалить объект по параметру
     * void deleteAll(List<UserDto> userDtoList) удалить список объектов
     */

    /**
     * Найти пользователя по его идентификатору
     *
     * @param userId идентификатор пользователя
     * @return пользователь
     */
    UserDto getDtoById(Long userId);

    /**
     * Найти пользователя по логину
     *
     * @param username логин пользователя
     * @return пользователь
     */
    UserDto getDtoByUsername(String username);

    /**
     * Получить Jwt Пользователя для генерации токенов
     *
     * @param username логин пользователя
     * @return общая сущность Пользователя
     */
    JwtUser getJwtByUsername(String username);

    /**
     * Получить общего Пользователя для межсервисного взаимодействия
     *
     * @param username логин пользователя
     * @return общая сущность Пользователя
     */
    User getByUsername(String username);

    /**
     * Найти всех пользователей
     *
     * @return список пользователей
     */
    List<UserDto> getAllDto();

    /**
     * Создать нового пользователя
     *
     * @param userRequest отображение данных пользователя
     * @return новый пользователь, сохраненный в репозитории
     */
    UserDto save(UserRequest userRequest);

    /**
     * Изменить данные пользователя по его username
     *
     * @param userDto пользователь с измененными данными
     * @param username логин пользователя
     */
    UserDto update(UserDto userDto, String username);

    /**
     * Изменить данные пользователя по его id
     *
     * @param userDto пользователь с измененными данными
     * @param id индекс пользователя
     */
    UserDto update(UserDto userDto, Long id);

    /**
     * Изменить данные пользователя (для внутреннего использования)
     *
     * @param userDto пользователь с измененными данными
     */
    User update(UserDto userDto);

    /**
     * Изменить пароль пользователя
     *
     * @param passwordRequest данные для изменения пароля
     * @param authentication авторизация
     * @return измененен ли пользователь
     */
    boolean updatePassword(PasswordRequest passwordRequest, Authentication authentication);

    /**
     * Удалить пользователя по его идентификатору
     *
     * @param userId идентификатор пользователя
     * @return удален ли пользователь
     */
    boolean deleteById(Long userId);
}
