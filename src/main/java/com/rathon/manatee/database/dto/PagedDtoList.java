package com.rathon.manatee.database.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedDtoList<T> {
    List<T> content;
    Integer page;
    Integer size;
    Integer totalCount;
    Integer totalPages;
    Boolean first;
    Boolean last;
}
