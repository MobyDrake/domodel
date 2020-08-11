package ru.geekbrains.domodel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.domodel.entities.Meter;
import ru.geekbrains.domodel.entities.User;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий счетчиков показаний
 */
@Repository
public interface MeterRepository extends JpaRepository<Meter, Long> {

    Optional<Meter> findByMeterNumber(Integer meterNum);
    Optional<List<Meter>> findAllByUser(User user);
    Optional<Meter> findByUser(User user);
}
