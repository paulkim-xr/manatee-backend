package com.rathon.manatee.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedList<T> {
    List<T> content;
    Integer page;
    Integer size;
    Integer totalCount;
    Integer totalPages;
    Boolean first;
    Boolean last;

    public static<T> PagedList<T> build(List<T> list, int page, int size, int totalCount) {
        PagedList<T> pagedList = new PagedList<>();
        pagedList.setContent(list);
        pagedList.setPage(page);
        pagedList.setSize(size);
        pagedList.setTotalCount(totalCount);
        pagedList.setTotalPages(Math.ceilDiv(totalCount, size));
        pagedList.setFirst(page == 0);
        pagedList.setLast(page == (pagedList.getTotalPages() - 1));

        return pagedList;
    }
}
