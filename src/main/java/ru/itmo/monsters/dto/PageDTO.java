package ru.itmo.monsters.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<T> {
    private List<T> content;
    private int currentPage;
    private int totalPages;
    private int size;
    private int numberOfElements;
    private long totalElements;
    private boolean hasNext;
}
