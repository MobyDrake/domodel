package ru.geekbrains.domodel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Dto представление сущности Документ
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentDto {

    private Integer id;

    // Заголовок (наименование)
    private String title;

    // Ссылка на адрес документа
    private String linkAddress;
}
