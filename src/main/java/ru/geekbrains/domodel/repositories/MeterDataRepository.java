package ru.geekbrains.domodel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.domodel.entities.Meter;
import ru.geekbrains.domodel.entities.MeterData;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий данных показаний счетчика
 */
public interface MeterDataRepository extends JpaRepository<MeterData, Long> {
    
    /**
     * Получить список показаний счетчика
     */
    Optional<List<MeterData>> findByMeter(Meter meter);
    
    //TODO получить последнее по дате (самое свежее) показание счетчика
}