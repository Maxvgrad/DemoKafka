package ru.demo.kafka.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDto {

    /**
     * GUID документа
     */
    private String internalId;

    /**
     * Тело документа
     */
    private String content;

    /**
     * ИНН из подписи документа
     */
    private String signInn;

    /**
     * Идентификатор черновика
     */
    private String draftId;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER, timezone = "UTC")
    private Date receivedAt;

    /**
     * Подпись
     */
    private String signature;

    /**
     * Тело документа в формате Base64
     */
    private String rawDocument;

    /**
     * Токен авторизации
     */
    private String token;

}

