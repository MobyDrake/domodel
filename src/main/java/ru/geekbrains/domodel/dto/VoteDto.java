package ru.geekbrains.domodel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.geekbrains.domodel.entities.Document;
import ru.geekbrains.domodel.entities.PhotoLink;
import ru.geekbrains.domodel.entities.VoteData;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Dto представление Голосования
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoteDto {

    private Long id;

    // Тема Опроса (до 50 знаков)
    private String title;

    // Предмет опроса (С учетом перехода текст до 500 знаков)
    private String text;

    // Дата начала опроса (не должна быть меньше текущей даты)
    private LocalDate startDate;

    // Дата завершения опроса (не должна быть меньше текущей даты и даты начала опроса)
    private LocalDate endDate;

    // Список ссылок на фото (2 шт.), загружается при необходимости
    private List<PhotoLink> photoLinks = new ArrayList<>();

    // Список ссылок на документы (2 шт.), загружается  при необходимости
     private List<Document> documents = new ArrayList<>();

    // Список голосов
    private List<VoteData> voteDatas = new ArrayList<>();

    // статус голосования
    private String statusVotes;
}
