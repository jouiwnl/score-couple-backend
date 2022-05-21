package com.joao.scorecouple.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    NOTSTARTED("NS", "notstarted"),
    CANCELED("CA", "canceled"),
    COMPLETED("CO", "completed"),
    DOING("DO", "doing");

    private String value;
    private String description;
}
