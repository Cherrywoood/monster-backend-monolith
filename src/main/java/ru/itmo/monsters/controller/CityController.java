package ru.itmo.monsters.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itmo.monsters.dto.CityDTO;
import ru.itmo.monsters.mapper.CityMapper;
import ru.itmo.monsters.service.CityService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("cities")
public class CityController {

    private final CityService cityService;
    private final CityMapper cityMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityDTO addCity(@Valid @RequestBody CityDTO cityDTO) {
        return cityMapper.mapEntityToDto(cityService.save(cityDTO));
    }

    @GetMapping
    // TODO: 10.10.2022 pagination
    public ResponseEntity<List<CityDTO>> findAll() {
        List<CityDTO> cityDTOS = new ArrayList<>();
        cityService.findAll().forEach(c -> cityDTOS.add(cityMapper.mapEntityToDto(c)));
        if (cityDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(cityDTOS, HttpStatus.OK);
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

