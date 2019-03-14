package ru.demo.kafka.boot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Документ")
public class DocumentDto {

    @ApiModelProperty(name = "GUID документа", hidden = true)
    private String internalId;

    @ApiModelProperty(name = "Тело документа")
    private String content;

    @ApiModelProperty(name = "ИНН из подписи документа")
    private String signInn;

    @ApiModelProperty(name = "Идентификатор черновика", hidden = true)
    private String draftId;

    @ApiModelProperty(name = "Дата получения", hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER, timezone = "UTC")
    private Date receivedAt;

    @ApiModelProperty(name = "Подпись", hidden = true)
    private String signature;

    @ApiModelProperty(name = "Тело документа в формате Base64", hidden = true)
    private String rawDocument;

    @ApiModelProperty(name = "Токен авторизации", hidden = true)
    private String token;
}

