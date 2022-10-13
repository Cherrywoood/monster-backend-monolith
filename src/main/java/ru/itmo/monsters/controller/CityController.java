package ru.itmo.monsters.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.CityDTO;
import ru.itmo.monsters.dto.ElectricBalloonDTO;
import ru.itmo.monsters.dto.PageDTO;
import ru.itmo.monsters.mapper.CityMapper;
import ru.itmo.monsters.mapper.PageMapper;
import ru.itmo.monsters.model.CityEntity;
import ru.itmo.monsters.model.ElectricBalloonEntity;
import ru.itmo.monsters.service.CityService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("cities")
@PreAuthorize("hasAuthority('ADMIN')")
public class CityController {

    private final CityService cityService;
    private final CityMapper cityMapper;
    private final PageMapper<CityDTO> pageMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityDTO addCity(@Valid @RequestBody CityDTO cityDTO) {
        return cityMapper.mapEntityToDto(cityService.save(cityDTO));
    }

    @GetMapping
    public ResponseEntity<PageDTO<CityDTO>> findAll(@RequestParam(defaultValue = "0")
                                                     @Min(value = 0, message = "must not be less than zero") int page,
                                                    @RequestParam(defaultValue = "5")
                                                     @Max(value = 50, message = "must not be more than 50 characters") int size) {

        Page<CityEntity> pages = cityService.findAll(page, size);
        if (pages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pageMapper.mapToDto(pages.map(cityMapper::mapEntityToDto)), HttpStatus.OK);
    }

    @DeleteMapping("{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable UUID cityId) {
        cityService.delete(cityId);
    }

    @PutMapping("{cityId}")
    @ResponseStatus(HttpStatus.OK)
    public CityDTO putCity(@PathVariable UUID cityId, @Valid @RequestBody CityDTO cityDTO) {
        return cityMapper.mapEntityToDto(cityService.updateById(cityId, cityDTO));
    }
}

