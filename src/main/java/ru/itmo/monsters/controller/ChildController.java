package ru.itmo.monsters.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.ChildDTO;
import ru.itmo.monsters.dto.PageDTO;
import ru.itmo.monsters.mapper.ChildMapper;
import ru.itmo.monsters.mapper.PageMapper;
import ru.itmo.monsters.model.ChildEntity;
import ru.itmo.monsters.service.ChildService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/child")
public class ChildController {

    private final ChildService childService;
    private final ChildMapper childMapper;
    private final PageMapper<ChildDTO> pageMapper;


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SCARE ASSISTANT')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChildDTO addChild(@Valid @RequestBody ChildDTO childDTO) {
        return childMapper.mapEntityToDto(childService.save(childDTO));
    }

    @PreAuthorize("hasAuthority('SCARE') or hasAuthority('ADMIN') or hasAuthority('SCARE ASSISTANT')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageDTO<ChildDTO>> getChildren(@RequestParam(defaultValue = "0")
                                                         @Min(value = 0, message = "must not be less than zero") int page,
                                                         @RequestParam(defaultValue = "5")
                                                         @Max(value = 50, message = "must not be more than 50 characters") int size) {
        Page<ChildEntity> pageChild = childService.getAll(page, size);
        if (pageChild.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(pageMapper.mapToDto(pageChild.map(childMapper::mapEntityToDto)), HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAuthority('SCARE') or hasAuthority('ADMIN') or hasAuthority('SCARE ASSISTANT')")
    @GetMapping("/scared")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageDTO<ChildDTO>> getScaredChildrenByDate(@RequestParam(defaultValue = "0")
                                                                     @Min(value = 0, message = "must not be less than zero") int page,
                                                                     @RequestParam(defaultValue = "5")
                                                                     @Max(value = 50, message = "must not be more than 50 characters") int size,
                                                                     @RequestParam(required = false) Date date) {
        Page<ChildEntity> pageChild = childService.getScaredChildrenByDate(page, size, date);

        if (pageChild.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(pageMapper.mapToDto(pageChild.map(childMapper::mapEntityToDto)), HttpStatus.OK);
        }
    }

}
