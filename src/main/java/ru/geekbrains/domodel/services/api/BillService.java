package ru.geekbrains.domodel.services.api;

import ru.geekbrains.domodel.entities.Account;
import ru.geekbrains.domodel.entities.Bill;
import ru.geekbrains.domodel.entities.constants.SendStatus;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс сервиса счетов (платежных документов)
 */
public interface BillService {

    /**
     * Получить список всех счетов
     */
    List<Bill> getAllBills();

    /**
     * Сохранить счет
     */
    Bill save(Bill bill);

    /**
     * Найти счет по его номеру
     */
    Bill findById(Long billId);

    /**
     * Найти все счета по имени пользователя
     */
    List<Bill> findAllByUsername(String username);

    /**
     * Найти все счета для аккаунта
     */
    List<Bill> getAllBillsByAccount(Account account);

    /**
     * создать счет для аккаунта
     */
    Bill createBillByAccount(Account account);

    /**
     * Создать счета для всех аккаунтов
     */
    List<Bill> createAll();

    List<Bill> getAllByAccount(Account account);

    List<Bill> getAllByStatus(SendStatus sendStatus);
    List<Bill> getAllByStatusAndAccount(SendStatus status, Account account);
    Bill changeById();

}
