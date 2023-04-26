package com.ghibo.bookserver.domain.dto;

import lombok.Data;

@Data
public class RabbitMqWrapper<E> {
    String domain;
    String type;
    E data;
    Object extraData;

    public RabbitMqWrapper(String domain, String type, E data, Object extraData) {
        this.domain = domain;
        this.type = type;
        this.data = data;
        this.extraData = extraData;
    }

    public RabbitMqWrapper() {
    }
}
