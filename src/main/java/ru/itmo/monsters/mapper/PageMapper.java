package ru.itmo.monsters.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.itmo.monsters.dto.PageDTO;

@RequiredArgsConstructor
@Component
public class PageMapper<T> {
    public PageDTO<T> mapToDto(Page<T> page) {
        return new PageDTO<>(
                page.getContent(),
                page.getTotalElements(),
                page.hasNext()
        );
    }

}
