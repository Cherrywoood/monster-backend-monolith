package ru.itmo.monsters.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.InfectionDTO;
import ru.itmo.monsters.dto.PageDTO;
import ru.itmo.monsters.mapper.InfectionMapper;
import ru.itmo.monsters.mapper.PageMapper;
import ru.itmo.monsters.model.InfectionEntity;
import ru.itmo.monsters.service.InfectionService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Date;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Validated
@RestController
@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DISINFECTOR')")
@RequestMapping("/infections")
public class InfectionController {
    private final InfectionService infectionService;
    private final PageMapper<InfectionDTO> pageMapper;
    private final InfectionMapper infectionMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InfectionDTO save(@Valid @RequestBody InfectionDTO infectionDTO) {
        return infectionMapper.mapEntityToDto(infectionService.save(infectionDTO));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InfectionDTO findById(@PathVariable UUID id) {
        return infectionMapper.mapEntityToDto(infectionService.findById(id));
    }

    @GetMapping
    public ResponseEntity<PageDTO<InfectionDTO>> findAll(@RequestParam(defaultValue = "0")
                                                         @Min(value = 0, message = "must not be less than zero") int page,
                                                         @RequestParam(defaultValue = "5")
                                                         @Max(value = 50, message = "must not be more than 50 characters") int size,
                                                         @RequestParam(required = false) UUID monsterId) {
        Page<InfectionEntity> pageInfection = infectionService.findAll(page, size, monsterId);
        if (pageInfection.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(pageMapper.mapToDto(pageInfection.map(infectionMapper::mapEntityToDto)), HttpStatus.OK);
        }

    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InfectionDTO updateCureDate(@RequestBody Map<String, Date> cureDate, @PathVariable UUID id) {
        return infectionMapper.mapEntityToDto(infectionService.updateCureDate(id, cureDate));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        infectionService.delete(id);
    }

}
