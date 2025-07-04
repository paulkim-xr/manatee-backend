package com.rathon.manatee.core.dto;

import lombok.Getter;

@Getter
public class SearchField {
    private final String name;
    private final String from;
    private final Class<?> type;

    public SearchField(String name, String searchFrom, Class<?> type) {
        this.name = name;
        this.from = searchFrom;
        this.type = type;
    }
}
