package ru.geekbrains.domodel.services.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.domodel.dto.NewUserDataDto;
import ru.geekbrains.domodel.dto.UserDto;
import ru.geekbrains.domodel.entities.Authority;
import ru.geekbrains.domodel.entities.User;
import ru.geekbrains.domodel.entities.common.JwtUser;
import ru.geekbrains.domodel.entities.common.UserCommon;
import ru.geekbrains.domodel.mappers.UserCommonMapper;
import ru.geekbrains.domodel.mappers.UserMapper;
import ru.geekbrains.domodel.repositories.AuthorityRepository;
import ru.geekbrains.domodel.repositories.UserRepository;
import ru.geekbrains.domodel.services.api.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.geekbrains.domodel.entities.constants.Roles.ROLE_USER;

/**
 * Сервис пользователей
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // Сервис шифрования паролей
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    // Репозиторий пользователей
    private final UserRepository userRepository;

    // Репозиторий ролей пользователя
    private final AuthorityRepository authorityRepository;

    @Override
    public UserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(userMapper::userToUserDto).orElse(null);
    }

    @Override
    public UserCommon getUserCommonByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(UserCommonMapper::userToUserCommon).orElse(null);
    }

    @Override
    public JwtUser getJwtUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(UserCommonMapper::userToJwtUser).orElse(null);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(userMapper::userToUserDto).orElse(null);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::userToUserDto).collect(Collectors.toList());
    }

    @Override
    public boolean deleteUserById(Long userId) {
        userRepository.deleteById(userId);
        return userRepository.existsById(userId);
    }

    @Override
    public UserDto saveUser(NewUserDataDto newData) {
        // TODO предусмотреть проверку полей
        Optional<User> optionalUser = userRepository.findByUsername(newData.getUsername());
        if (optionalUser.isPresent()) {
            return null;
        }

        User newUser = new User(
                newData.getUsername(),
                passwordEncoder.encode(newData.getPassword()),
                true,
                LocalDate.now());
        Authority authority = authorityRepository.findByAuthority(ROLE_USER);
        newUser.getAuthorities().add(authority);
        return userMapper.userToUserDto(userRepository.save(newUser));
    }

    public void updateUser(UserDto userDto, String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            return;
        }
        if (userDto.getPhone() != null && !userDto.getPhone().isEmpty()) {
            user.setUsername(userDto.getPhone());
        }
        if (userDto.getFirstName() != null && !userDto.getFirstName().isEmpty()) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null && !userDto.getLastName().isEmpty()) {
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getPatronymic() != null && !userDto.getPatronymic().isEmpty()) {
            user.setPatronymic(userDto.getPatronymic());
        }
        if (userDto.getEmail() != null && !userDto.getEmail().isEmpty()) {
            user.setEmail(userDto.getEmail());
        }
        userRepository.save(user);
    }
}
