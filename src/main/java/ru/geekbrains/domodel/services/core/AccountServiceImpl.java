package ru.geekbrains.domodel.services.core;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.domodel.dto.AccountDto;
import ru.geekbrains.domodel.mappers.AccountMapper;
import ru.geekbrains.domodel.repositories.AccountRepository;
import ru.geekbrains.domodel.services.api.AccountService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса лицевых счетов
 */
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    // Репозиторий лицевых счетов
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountDto getAccountById(Long id) {
        return accountRepository.findById(id)
                .map(accountMapper::accountToAccountDto)
                .orElseThrow(() -> new RuntimeException("account not exist"));
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(accountMapper::accountToAccountDto).collect(Collectors.toList());
    }

    @Override
    public List<AccountDto> getAccountsByUserUsername(String username) {
        return accountRepository.findAllByUserUsername(username).stream()
                .map(accountMapper::accountToAccountDto).collect(Collectors.toList());
    }
}
