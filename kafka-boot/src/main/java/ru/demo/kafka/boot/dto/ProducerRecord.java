package ru.demo.kafka.boot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ProducerRecord {

    @ApiModelProperty
    @JsonProperty
    private String topic;

    @ApiModelProperty
    @JsonProperty
    private Integer partition;

    @ApiModelProperty
    @JsonProperty
    private String key;

    @ApiModelProperty
    @JsonProperty
    private String value;

    @ApiModelProperty
    @JsonProperty
    private Instant timestamp;
}

